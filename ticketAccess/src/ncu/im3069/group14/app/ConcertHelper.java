package ncu.im3069.group14.app;

import java.sql.*;
import org.json.*;
import ncu.im3069.group14.util.Mysqlconnect;
import ncu.im3069.group14.util.DBMgr;
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
			/** ���o��Ʈw���s�u */
            conn = DBMgr.getConnection();
            
            /** sql���O  */
            String sql = "INSERT INTO testconcert(concertName) VALUES ('?')";
            pres = conn.prepareStatement(sql);
            pres.setString(1, c.getConcertName());
            /** ����s�W��SQL���O�ðO���v�T����� */
            row = pres.executeUpdate();
            
		}catch(SQLException e) {
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
            DBMgr.close(pres, conn);
        }
		
        JSONObject response = new JSONObject();
        response.put("row", row);
        return response;
	}
}