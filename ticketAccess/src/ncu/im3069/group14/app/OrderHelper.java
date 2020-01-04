package ncu.im3069.group14.app;


import ncu.im3069.group14.util.MysqlConnect;
import java.sql.*;
import java.util.ArrayList;

import org.json.*;


public class OrderHelper {
	
	private Connection conn = null;
	private PreparedStatement pres = null;
	
	private static OrderHelper oh;
	
	public static OrderHelper getHelper() {
		if ( oh == null ) {
			oh = new OrderHelper();
		}
		return oh;
	}
	/**
	 * 給訂單id，去ticketid撈資料，用jsonarray包起來後，回傳該會員所有的訂單
	 * @param orderid
	 * @return
	 */
	//這個還沒完成
	public JSONObject getOrder(int orderid) {
		int row = 0;
		String query = ""; //要執行的query
		String exexcute_sql = ""; //mysql執行的query
		JSONObject response = new JSONObject();;
		JSONObject temp = null;
		Order o = null;
		ResultSet rs = null;
		System.out.println("orderid:"+orderid);
		
		try {
			conn = MysqlConnect.getConnect();
			query = "SELECT * FROM `missa`.`order` as `a`inner join `missa`.`ticket` as `b` on `a`.`idorder` = `b`.`orderid` where `memberid` = ? ";
			pres = conn.prepareStatement(query);
			pres.setInt(1, memberid);
			
			rs = pres.executeQuery();
			exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            while(rs.next()) {
            	row++;
            	int idorder = rs.getInt("idorder");
            	String payment = rs.getString("payment");
            	Boolean paid = rs.getBoolean("paid");
            	int ticketamount = rs.getInt("ticketamount");
            	Timestamp createtime = rs.getTimestamp("createtime");
            	int concertid = rs.getInt("concertid");
            	
            	o = new Order(idorder, memberid, payment, paid, ticketamount, createtime, concertid);
            	temp = o.toJsonData(idorder);
            	jsa.put(temp);
            }
			
		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close(rs, pres, conn);
        }
		response.put("result", "get all data success");
		response.put("row", row);
		response.put("dataset", jsa);
		return response;
	}
	/**
	 * 給會員id，去order撈資料，用jsonarray包起來後，回傳該會員所有的訂單
	 * 目前這樣查詢會是以ticket為結果
	 * @param memberid
	 * @return
	 */
	public JSONObject getAllOrder(int memberid) {
		int row = 0;
		int tempconcertid = 0;//用來暫存上一筆資料的concertid
		String query = ""; //要執行的query
		String exexcute_sql = ""; //mysql執行的query
		JSONObject response = new JSONObject();;
		JSONObject temp = null;
		JSONArray jsa = new JSONArray();
		Order o = null;
		ResultSet rs = null;
		System.out.println("memberid:"+memberid);
		
		try {
			conn = MysqlConnect.getConnect();
			query = "SELECT * FROM `missa`.`order` as `a`inner join `missa`.`ticket` as `b` on `a`.`idorder` = `b`.`orderid` where `memberid` = ? order by `concertid` ASC";
			pres = conn.prepareStatement(query);
			pres.setInt(1, memberid);
			
			rs = pres.executeQuery();
			exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            while(rs.next()) {
            	
            	int concertid = rs.getInt("concertid");
            	//如果不是第一筆，那就比較concertid，如果依樣，就跳過
            	if (row > 0 && concertid == tempconcertid) {	
            		continue;
            	}
            	row++;
            	
            	tempconcertid = concertid;
            	int idorder = rs.getInt("idorder");
            	String payment = rs.getString("payment");
            	Boolean paid = rs.getBoolean("paid");
            	int ticketamount = rs.getInt("ticketamount");
            	Timestamp createtime = rs.getTimestamp("createtime");
            	int totalprice = rs.getInt("totalprice");
            	
            	o = new Order(idorder, memberid, payment, paid, ticketamount, createtime, concertid,totalprice);
            	temp = o.toJsonData(idorder);
            	jsa.put(temp);
            }
			
		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close(rs, pres, conn);
        }
		if ( row > 0 ) {
			response.put("result", "get member all data success");
			response.put("row", row);
			response.put("orders", jsa);
		}else {
			response.put("result", "no data or error happened");
			response.put("row", row);
		}
		
		return response;
	}
	public JSONObject create(Order o) {
		int row = 0;//用來記錄改變了幾行Mysql
		int idorder = 0;//用來記錄新增order的pk id
		int ticketbuy = 0;//已經+買了幾張ticket
		String query = ""; //要執行的query
		String exexcute_sql = ""; //mysql執行的query
		JSONObject response = new JSONObject();;
		JSONObject data = null;
		
		try {
			ticketbuy = checkTicketAmount(o.getMemberid(), o.getConcertid());
			
			//checkTicketAmount回傳已經購買的數量，再加上這次的數量，如果大於4就不能買
			//如果大於零但是小於4，可以購買，update order
			//但是當每一筆都已經付款，那要重新新增一筆新的訂單 
			if (  ticketbuy + o.getTicketamount() > 4 ) {
				System.out.println("已經超過購買的票數");
				response.put("result", "already full");
				
			}else if (ticketbuy + o.getTicketamount() <= 4 && ticketbuy > 0 && ! getPaidStatus(o.getMemberid(),o.getConcertid())){
				
				conn = MysqlConnect.getConnect(); 
				
				//STEP0 更新order物件的ticketamount
				o.updateAmount(ticketbuy+ o.getTicketamount());
				
				//STEP1 更新order資料庫
				query = "update `missa`.`order`  "
				 		+ "inner join `missa`.`ticket` "
				 		+ "on `order`.`idorder` = `ticket`.`orderid` "
				 		+ "set `ticketamount` = ? "
				 		+ "where `memberid` = ? and `concertid` = ? ";
				 pres = conn.prepareStatement(query);
				 
				 pres.setInt(1, o.getTicketamount());
				 pres.setInt(2, o.getMemberid());
				 pres.setInt(3, o.getConcertid());
				 
				 row = pres.executeUpdate();
				 exexcute_sql = pres.toString();
				 System.out.println(exexcute_sql);
				 
				 //STEP2 查詢				 
				 query = "SELECT * FROM `missa`.`order` as `a` inner join `missa`.`ticket` as `b` on `a`.`idorder` = `b`.`orderid` where `memberid` = ? and `concertid` = ? ";
				 pres = conn.prepareStatement(query);
				 pres.setInt(1, o.getMemberid());
				 pres.setInt(2, o.getConcertid());
				 ResultSet rs = pres.executeQuery();
				
				 //STEP3 回傳結果
				 response.put("result", "update order success");
				 if(rs.next()){
					 idorder = rs.getInt("idorder");
					 data = o.toJsonData(idorder);
					 response.put("row", row);
					 response.put("order", data);
				 }
				 
				 
			}else {
				conn = MysqlConnect.getConnect();
				query = "INSERT INTO `missa`.`order`(`memberid`, `payment`, `paid`, `ticketamount`, `createtime`,`totalprice`)"
				+" VALUES(?, ?, ?, ?, ?, ?)";
				pres = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				
				pres.setInt(1, o.getMemberid());
				pres.setString(2, o.getPayment());
				pres.setBoolean(3, o.getPaid());
				pres.setInt(4, o.getTicketamount());
				pres.setTimestamp(5, o.getCreatetime());
				pres.setInt(6, o.getTotalprice());
				
				row = pres.executeUpdate();
				exexcute_sql = pres.toString();
				System.out.println(exexcute_sql);
				
				ResultSet rs = pres.getGeneratedKeys();
				rs.next();
				idorder = rs.getInt(1); 
				o.setIdorder(idorder);
				data = o.toJsonData(idorder);
				
				response.put("result", "create order success");
				response.put("row", row);
				response.put("order", data);
			}			
			
		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close( pres, conn);
        }
		
		return response;
	}
	/**
	 * 
	 * @param memberid 該訂單中member的id
	 * @param concertid 該訂單中 的 concert的id
	 * @return 回傳已經購買的數量
	 */
	public int checkTicketAmount(int memberid, int concertid) {
		int amount = 0; // 已經購買的票數
		ResultSet rs = null; //mysql回傳的結果
		String exexcute_sql = "";//mysql回傳執行的指令
		
		try {
			conn = MysqlConnect.getConnect();
			//把兩個table結合再一起，然後再去撈資料
			String query = "select * "
					+ "from `missa`.`order`"
					+ "inner join `missa`.`ticket`"
					+ "on `order`.`idorder` = `ticket`.`orderid`"
					+ "where `memberid` = ? and `concertid` = ? ";
			pres = conn.prepareStatement(query);
			pres.setInt(1, memberid);
			pres.setInt(2, concertid);
			rs = pres.executeQuery();
			
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
			//回傳撈出的資料數量，因為一筆就代表有一個ticket
			while(rs.next()) {
				amount += 1;
			}

		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close(rs, pres, conn);
        }
		System.out.println("ticketamount:"+amount);
		return amount;
	}
	public JSONObject cancelOrder(int idorder) {
		int row = 0;
		String query = "";
		String execute_sql = "";
		JSONObject response = new JSONObject();
		
		try {
			conn = MysqlConnect.getConnect();
			query = "delete from `missa`.`order` where `idorder` = ? ";
			pres = conn.prepareStatement(query);
			pres.setInt(1, idorder);
			
			row = pres.executeUpdate();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close( pres, conn);
        }
		if ( row > 0 ) {
			response.put("result", "delete order success");
			response.put("row", row);
			response.put("orderid", idorder);
		}else {
			response.put("result", "delete order failed");
			response.put("row", row);
			response.put("orderid", idorder);
		}
		
		return response;
		
	}
	public JSONObject getPayment(int idorder) {
		
		String query = "";
		String execute_sql = "";
		String payment = "none";
		JSONObject response = new JSONObject();
		ResultSet rs = null;
		
		try {
			conn = MysqlConnect.getConnect();
			query = "SELECT * FROM `missa`.`order` where `idorder` = ? ";
			pres = conn.prepareStatement(query);
			pres.setInt(1, idorder);
			
			rs = pres.executeQuery();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
			rs.next();
			payment = rs.getString("payment");
			
		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close( pres, conn);
        }
		
		if (payment == "none") {
			response.put("payment", "none");
			response.put("result", "query failed");
			return response;
		}
		response.put("result", "get payment success");
		response.put("payment", payment);
		response.put("orderid", idorder);
		return response;
	}
	public JSONObject getPaidOrder(int idorder) {
		int row = 0;
		
		String query = "";
		String execute_sql = "";
		JSONObject response = new JSONObject();
		JSONObject data = getPayment(idorder);
		String payment = data.getString("payment");
		if (payment == "none") {
			response.put("result", "paid failed, didn't find order");
			return response;
		}
		
		try {
			conn = MysqlConnect.getConnect();
			query = "update missa.order " + 
					"set paid = true " + 
					"where idorder = ? ";
			pres = conn.prepareStatement(query);
			pres.setInt(1, idorder);
			
			row = pres.executeUpdate();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close( pres, conn);
        }
		if (row > 0) {
			response.put("result", "order paid success");
			response.put("payment", payment);
			response.put("row", row);
		}else {
			response.put("result", "order paid failed");
			response.put("row", row);
		}
		
		return response;
	}
	public boolean getPaidStatus(int memberid, int concertid) {
		String query = "";
		String execute_sql = "";
		boolean paid = false;
		ResultSet rs = null;
		try {
			conn = MysqlConnect.getConnect();
			query = "SELECT * FROM `missa`.`order` as `a` join `missa`.`ticket` as `b` on `a`.`idorder` = `b`.`orderid` where memberid = ? and concertid = ?;";
			pres = conn.prepareStatement(query);
			pres.setInt(1, memberid);
			pres.setInt(2, concertid);
			
			rs = pres.executeQuery();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
			while(rs.next()) {
				paid = rs.getBoolean("paid");
				if (paid == false) {
					break;
				}
			}
			
		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close( pres, conn);
        }
		
		return paid;
		
	}
}
