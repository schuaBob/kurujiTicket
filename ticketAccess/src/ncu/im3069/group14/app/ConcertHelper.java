package ncu.im3069.group14.app;

import java.sql.*;
import org.json.*;
import ncu.im3069.group14.util.MysqlConnect;
import java.text.MessageFormat;
import java.util.ArrayList;

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
            conn = MysqlConnect.getConnect();
            /** sql���O  */
            String sql = "INSERT INTO concert(name,supplierid,location,picture,seatpicture,endsellingtime,content,ticketstatus,concertstarttime,concertendtime) VALUES (?,?,?,?,?,?,?,?,?,?)";
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
        	MysqlConnect.close(pres, conn);
        }
		
        JSONObject response = new JSONObject();
        response.put("row", row);
        return response;
	}
	public JSONArray getConcertBySession(String session) {	
		
		ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
		JSONArray result = new JSONArray();
		try {
			ResultSet rs = null;
			conn = MysqlConnect.getConnect();
			
			if(session != null && !session.equals("")) {
				String sql = "SELECT * FROM concert WHERE session = ?";
				pres = conn.prepareStatement(sql);
				pres.setString(1, session);
				rs = pres.executeQuery();	
			}else {
				String sql = "SELECT * FROM concert";
				pres = conn.prepareStatement(sql);
				pres.setString(1, session);
				rs = pres.executeQuery();
			}							
			
			ResultSetMetaData rsmd = null;
			String columnName = null;
			
			while(rs.next()) {				
				JSONObject temp = new JSONObject();
				rsmd = rs.getMetaData();				
				for(int i=0;i<rsmd.getColumnCount();i++) {
					columnName = rsmd.getColumnName(i+1);
					temp.put(columnName, rs.getString(columnName));
				}
				arrayList.add(temp);
			}
			result = new JSONArray(arrayList);
		}catch(SQLException e) {
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
        	MysqlConnect.close(pres, conn);
        }			
		return result;
	}
}
