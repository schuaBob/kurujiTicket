package ncu.im3069.group14.app;

import java.sql.*;
import org.json.*;
import ncu.im3069.group14.util.MysqlConnect;
import java.text.MessageFormat;
import java.util.ArrayList;
import ncu.im3069.group14.tools.RequestHandler;

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
			
			if(!"".equals(session)) {
				String sql = "SELECT * FROM concert WHERE session = ?";
				pres = conn.prepareStatement(sql);
				pres.setString(1, session);
				rs = pres.executeQuery();	
			}else {
				String sql = "SELECT * FROM concert";
				pres = conn.prepareStatement(sql);
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
	
	
	
	//bobo �g���Aorder�ݭn���\��
	/**
	 * ���o�s�W���骺��mID
	 * @param concertid ���@���t�۷|
	 * @param seatarea ���@�Ϫ��y��
	 * @param ticketamount �Ψӧ�sticketstatus
	 */
	public int getSeatId(int concertid, String seatarea, int ticketamount) {

		int seatid = 0;
		String query = "";
		String execute_sql = "";
		JSONObject ticketstatus = null;
		JSONArray ticketstatusarray = new JSONArray();
		ResultSet rs = null;
		
		//STEP1 ���h��Ʈw���o�Ӻt�۷|��ticketstatus
		try {
			conn = MysqlConnect.getConnect();
			query = "SELECT * FROM missa.concert where idconcert = ?";
			pres = conn.prepareStatement(query);
			pres.setInt(1, concertid);
			
			rs = pres.executeQuery();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
			while(rs.next()) {
				
				// STEP1.5 ���oticketstatus�����}�Cticketstatusarray
				ticketstatus = new JSONObject(rs.getString("ticketstatus"));
				ticketstatusarray = ticketstatus.getJSONArray("data");
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

		//STEP2 �qticketstatus ���o�ӰϦ쪺��T
		for (int i = 0; i < ticketstatusarray.length(); i++ ) {
			JSONObject jso = ticketstatusarray.getJSONObject(i);
			String area = jso.getString("Area");//���o��array[i]����area
			
			//STEP3 ����O�_�P�q����m�ۦP�A�p�G�ۦP�A���o�_�l��m
			if ( area.equals(seatarea) == true ) {
				seatid = jso.getInt("Sold");
				
				//STEP4 �ק�ticketstaus����sold�ƶq(�쥻���[�W�o���R���ƶq)
				jso.put("Sold", seatid+ticketamount);
				
			}
		}
		
		//STEP5 �ק�ticketstatus
		ticketstatus = new JSONObject();
		ticketstatus.put("data", ticketstatusarray);
		
		//STEP6 ��sconcert��Ʈw
		try {
			
			conn = MysqlConnect.getConnect();
			query = "update missa.concert " + 
					"set ticketstatus = ? " + 
					"where idconcert = ? ";
			pres = conn.prepareStatement(query);
			pres.setString(1, ticketstatus.toString());
			pres.setInt(2, concertid);
			
			pres.executeUpdate();
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

		System.out.println("finish getSeatID, return"+seatid);
		return seatid;
	}
}
