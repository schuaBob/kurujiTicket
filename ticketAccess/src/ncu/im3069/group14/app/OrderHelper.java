package ncu.im3069.group14.app;



import ncu.im3069.group14.util.Mysqlconnect;
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
	
	public JSONObject getOrder() {
		JSONObject response = new JSONObject();
		response.put("bobo", "is a good boy");
		return response;
	}
	public JSONObject create(Order o) {
		int row = 0;
		int idorder = 0;
		String exexcute_sql = "";
		JSONObject response = new JSONObject();;
		JSONObject data;
		
		try {
			conn = Mysqlconnect.getConnect();
			String query = "INSERT INTO `missa`.`order`(`memberid`, `payment`, `paid`, `ticketamount`, `createtime`)"
			+" VALUES(?, ?, ?, ?, ?)";
			pres = conn.prepareStatement(query);
			
			pres.setInt(1, o.getMemberid());
			pres.setString(2, o.getPayment());
			pres.setBoolean(3, o.getPaid());
			pres.setInt(4, o.getTicketamount());
			pres.setTimestamp(5, o.getCreatetime());
			
			
			row = pres.executeUpdate();
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
			
			ResultSet rs = pres.getGeneratedKeys();
			rs.next();
			idorder = rs.getInt("idorder");
			
			
			
			
			
		} catch (SQLException e) {
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
            Mysqlconnect.close( pres, conn);
            
        }
		data = o.toJsonData(idorder);
		response.put("row", row);
		response.put("data", data);
		return response;
	}
	
}
