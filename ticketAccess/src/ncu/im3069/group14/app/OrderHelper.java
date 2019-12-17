package ncu.im3069.group14.app;


import ncu.im3069.group14.util.Mysqlconnect;
import java.sql.*;
import org.json.*;


public class OrderHelper {
	
	private static OrderHelper oh;
	private Connection conn = null;
	private PreparedStatement pres = null;
	
	public JSONObject getOrder() {
		JSONObject response = new JSONObject();
		response.put("bobo", "is a good boy");
		return response;
	}
	public JSONObject create(Order o) {
		int row = 0;
		JSONObject data = new JSONObject();
		String exexcute_sql = "";
		
		try {
			conn = Mysqlconnect.getConnect();
			String query = "INSERT INTO `missa`.`order`(`memberid`,`payment`,`paid`,`ticketamount`,createtime`)"+"VALUES(?,?,?,?,?,?)";
			pres = conn.prepareStatement(query);
			
			pres.setInt(1, o.getMemberid());
			pres.setString(2, o.getPayment());
			pres.setBoolean(3, o.getPaid());
			pres.setInt(4, o.getTicketamount());
			pres.setDate(5, (java.sql.Date) o.getCreatetime());
			
			row = pres.executeUpdate();
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
			
		} catch (SQLException e) {
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
            Mysqlconnect.close( pres, conn);
        }
		return data;
	}
	
}
