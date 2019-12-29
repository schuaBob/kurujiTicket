package ncu.im3069.group14.app;

import java.sql.*;
import ncu.im3069.group14.util.MysqlConnect;
import org.json.*;

public class TicketHelper {
	private Connection conn = null;
	private PreparedStatement pres = null;
	private static TicketHelper th;
	
	public static TicketHelper getHelper() {
		if ( th == null ) {
			th = new TicketHelper();
		}
		return th;
	}
	public JSONObject create(Ticket t, int ticketamount) {
		int row = 0; //總共影響了幾行mysql
		int idticket = 0; //暫存idticket
		int createamount = 0; //已經產生了幾張票
		String query = ""; //要執行的query
		String execute_sql = ""; //mysql執行的query
		JSONObject response = new JSONObject();
		JSONObject data = null;
		JSONArray jsa = new JSONArray();
		
		try {
			conn = MysqlConnect.getConnect();
			query = "insert into missa.ticket (concertid,orderid,seatarea,seatid,isused) values(?,?,?,?,?);";
			pres = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pres.setInt(1, t.getConcertid());
			pres.setInt(2, t.getOrderid());
			pres.setString(3, t.getSeatarea());
			pres.setBoolean(5, t.getIsused());
			
			//當已經產生的票券少於票券總數時執行
			while(createamount < ticketamount) {
				t.setSeatid(createamount+t.getSeatid());//下一張票的seatid會比前一張多一
				pres.setInt(4, t.getSeatid());
				createamount++;
				
				row = pres.executeUpdate();
				execute_sql = pres.toString();
				System.out.println(execute_sql);
				
				ResultSet rs = pres.getGeneratedKeys();
				rs.next();
				idticket = rs.getInt(1);
				t.setIdticket(idticket);
				data = t.toJsonData(idticket);
				jsa.put(data);
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
		System.out.println("finish tickethelper create");
		response.put("result","create ticket success");
		response.put("row",row);
		response.put("data",data);
		return response;
	}
	
	public JSONObject getTicket(int orderid) {
		int row = 0;
		JSONObject response = new JSONObject();;
		JSONArray jsa = new JSONArray();
		ResultSet rs = null;
		System.out.println("start get ticket");
		
		try {
			conn = MysqlConnect.getConnect();
			pres = conn.prepareStatement("select * from `missa`.`ticket` where `orderid` = ? ");
			pres.setInt(1, orderid);
			rs = pres.executeQuery();
            System.out.println(pres.toString());
            
            while(rs.next()) {
            	row++;
            	Ticket t = new Ticket(rs.getInt("idticket"), rs.getInt("concertid"), orderid, rs.getString("seatarea"), rs.getInt("seatid"), rs.getBoolean("isused"));
            	jsa.put(t.toJsonData(t.getIdticket()));
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
		response.put("tickets", jsa);
		return response;
	}
	
	public JSONObject useTicket(int idticket) {
		int row = 0;
		JSONObject response = new JSONObject();
		
		try {
			conn = MysqlConnect.getConnect();
			pres = conn.prepareStatement("update missa.ticket set isused = true where idticket = ? ");
			pres.setInt(1, idticket);
			row = pres.executeUpdate();
            System.out.println(pres.toString());
			
		} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            MysqlConnect.close(pres, conn);
        }
		response.put("result", "ticket used success");
		response.put("idticket", idticket);
		response.put("row", row);
		return response;
	}
	
	public JSONObject cancelTicket(int idorder) {
		int row = 0;
		String query = "";
		String execute_sql = "";
		JSONObject response = new JSONObject();
		
		try {
			conn = MysqlConnect.getConnect();
			query = "delete from `missa`.`ticket` where `orderid` = ? ";
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
		response.put("result", "delete ticket success");
		response.put("row", row);
		return response;
	}
}
