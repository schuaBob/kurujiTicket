package ncu.im3069.group14.app;

import java.sql.*;
import org.json.*;
import ncu.im3069.group14.util.Mysqlconnect;
import java.text.MessageFormat;

public class ConcertHelper {
	
	private ConcertHelper() {
		
	}
	
	private static ConcertHelper ch;
	
	private Connection conn = null;	
	private PreparedStatement pres = null;
	
	public static ConcertHelper getHelper() {
		if(ch==null) {
			ch = new ConcertHelper();
		}
		return ch;
	}
	
//	public Boolean checkDuplicate(Supplier s) {
//		
//	}
//	
//	public JSONObject getBySupplierId(Integer id) {
//		
//	}
//	public JSONObject getAll() {
//		
//	}
//	public JSONObject update(Concert c) {
//		
//	}
	public JSONObject createConcert(Concert c) {
		
		int row = 0;
				
		try {
			/** 取得資料庫之連線 */
            conn = Mysqlconnect.getConnect();
            /** sql指令  */
            String sql = "INSERT INTO testconcert(name,supplierid,location,picture,seatpicture,endsellingtime,content,ticketstatus,concertstarttime,concertendtime) VALUES (?,?,?,?,?,?,?,?,?,?)";
            pres = conn.prepareStatement(sql);
            pres.setString(1, c.getConcertName());
            pres.setString(2, c.getSupplierId().toString());
            pres.setString(3, c.getLocation());
            pres.setString(4, c.getPicture());
            pres.setString(5, c.getSeatPicture());
            pres.setString(6, c.getEndSellingTime());
            pres.setString(7, c.getContent());
            pres.setString(8, c.getTicketStatus().toString());
            pres.setString(9, c.getConcertStartTime());
            pres.setString(10, c.getConcertEndTime());
            System.out.println(pres.toString());
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
		}catch(SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
        	Mysqlconnect.close(pres, conn);
        }
		
        JSONObject response = new JSONObject();
        response.put("row", row);
        return response;
	}
}
