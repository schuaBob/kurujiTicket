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
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            Mysqlconnect.close( pres, conn);
        }
		return data;
	}
	
}
