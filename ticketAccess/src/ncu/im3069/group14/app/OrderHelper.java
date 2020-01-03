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
	 * ���q��id�A�hticketid����ơA��jsonarray�]�_�ӫ�A�^�Ǹӷ|���Ҧ����q��
	 * @param orderid
	 * @return
	 */
	//�o���٨S����
	public JSONObject getOrder(int orderid) {
		int row = 0;
		String query = ""; //�n���檺query
		String exexcute_sql = ""; //mysql���檺query
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
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
            MysqlConnect.close(rs, pres, conn);
        }
		response.put("result", "get all data success");
		response.put("row", row);
		response.put("dataset", jsa);
		return response;
	}
	/**
	 * ���|��id�A�horder����ơA��jsonarray�]�_�ӫ�A�^�Ǹӷ|���Ҧ����q��
	 * �ثe�o�ˬd�߷|�O�Hticket�����G
	 * @param memberid
	 * @return
	 */
	public JSONObject getAllOrder(int memberid) {
		int row = 0;
		int tempconcertid = 0;//�ΨӼȦs�W�@����ƪ�concertid
		String query = ""; //�n���檺query
		String exexcute_sql = ""; //mysql���檺query
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
            	//�p�G���O�Ĥ@���A���N���concertid�A�p�G�̼ˡA�N���L
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
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
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
		int row = 0;//�ΨӰO�����ܤF�X��Mysql
		int idorder = 0;//�ΨӰO���s�Worder��pk id
		int ticketbuy = 0;//�w�g+�R�F�X�iticket
		String query = ""; //�n���檺query
		String exexcute_sql = ""; //mysql���檺query
		JSONObject response = new JSONObject();;
		JSONObject data = null;
		
		try {
			ticketbuy = checkTicketAmount(o.getMemberid(), o.getConcertid());
			
			//checkTicketAmount�^�Ǥw�g�ʶR���ƶq�A�A�[�W�o�����ƶq�A�p�G�j��4�N����R
			//�p�G�j��s���O�p��4�A�i�H�ʶR�Aupdate order
			//���O��C�@�����w�g�I�ڡA���n���s�s�W�@���s���q�� 
			if (  ticketbuy + o.getTicketamount() > 4 ) {
				System.out.println("�w�g�W�L�ʶR������");
				response.put("result", "already full");
				
			}else if (ticketbuy + o.getTicketamount() <= 4 && ticketbuy > 0 && ! getPaidStatus(o.getMemberid(),o.getConcertid())){
				
				conn = MysqlConnect.getConnect(); 
				
				//STEP0 ��sorder����ticketamount
				o.updateAmount(ticketbuy+ o.getTicketamount());
				
				//STEP1 ��sorder��Ʈw
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
				 
				 //STEP2 �d��				 
				 query = "SELECT * FROM `missa`.`order` as `a` inner join `missa`.`ticket` as `b` on `a`.`idorder` = `b`.`orderid` where `memberid` = ? and `concertid` = ? ";
				 pres = conn.prepareStatement(query);
				 pres.setInt(1, o.getMemberid());
				 pres.setInt(2, o.getConcertid());
				 ResultSet rs = pres.executeQuery();
				
				 //STEP3 �^�ǵ��G
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
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
            MysqlConnect.close( pres, conn);
        }
		
		return response;
	}
	/**
	 * 
	 * @param memberid �ӭq�椤member��id
	 * @param concertid �ӭq�椤 �� concert��id
	 * @return �^�Ǥw�g�ʶR���ƶq
	 */
	public int checkTicketAmount(int memberid, int concertid) {
		int amount = 0; // �w�g�ʶR������
		ResultSet rs = null; //mysql�^�Ǫ����G
		String exexcute_sql = "";//mysql�^�ǰ��檺���O
		
		try {
			conn = MysqlConnect.getConnect();
			//����table���X�A�@�_�A�M��A�h�����
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
			//�^�Ǽ��X����Ƽƶq�A�]���@���N�N���@��ticket
			while(rs.next()) {
				amount += 1;
			}

		} catch (SQLException e) {
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
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
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
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
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
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
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
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
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
            MysqlConnect.close( pres, conn);
        }
		
		return paid;
		
	}
}
