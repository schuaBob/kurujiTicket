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
			/** 嚙踝蕭嚙緻嚙踝蕭w嚙踝蕭嚙編嚙線 */
            conn = MysqlConnect.getConnect();
            /** sql嚙踝蕭嚙瞌  */
            String sql = "INSERT INTO concert(name,supplierid,location,picture,seatpicture,endsellingtime,content,ticketstatus,concertstarttime,concertendtime,session) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
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
            pres.setString(11, c.getSession());
            System.out.println(pres.toString());
            /** 嚙踝蕭嚙踝蕭s嚙磕嚙踝蕭SQL嚙踝蕭嚙瞌嚙衛記嚙踝蕭嚙緞嚙確嚙踝蕭嚙踝蕭嚙� */
            row = pres.executeUpdate();
            
		}catch(SQLException e) {
            /** 嚙盤嚙碼JDBC SQL嚙踝蕭嚙瞌嚙踝蕭嚙羯 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 嚙磐嚙踝蕭嚙羯嚙篁嚙盤嚙碼嚙踝蕭嚙羯嚙確嚙踝蕭 */
            e.printStackTrace();
        } finally {
            /** 嚙踝蕭嚙踝蕭嚙編嚙線嚙踝蕭嚙踝蕭嚙踝蕭狾嚙踝蕭嚙複庫嚙踝蕭嚙踝蕭嚙踝蕭嚙赭源 **/
        	MysqlConnect.close(pres, conn);
        }
		
        JSONObject response = new JSONObject();
        response.put("row", row);
        return response;
	}
	public JSONArray getConcertByAttr(String attr, String value) {	
		
		ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
		JSONArray result = new JSONArray();
		try {
			ResultSet rs = null;
			conn = MysqlConnect.getConnect();
			
			if(!"".equals(value)) {
				String sql = "SELECT * FROM concert WHERE "+attr+" = ?";
				pres = conn.prepareStatement(sql);
				pres.setString(1, value);
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
            /** 嚙盤嚙碼JDBC SQL嚙踝蕭嚙瞌嚙踝蕭嚙羯 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 嚙磐嚙踝蕭嚙羯嚙篁嚙盤嚙碼嚙踝蕭嚙羯嚙確嚙踝蕭 */
            e.printStackTrace();
        } finally {
            /** 嚙踝蕭嚙踝蕭嚙編嚙線嚙踝蕭嚙踝蕭嚙踝蕭狾嚙踝蕭嚙複庫嚙踝蕭嚙踝蕭嚙踝蕭嚙赭源 **/
        	MysqlConnect.close(pres, conn);
        }			
		return result;
	}
	
	
	
	//bobo 嚙篇嚙踝蕭嚙璀order嚙豎要嚙踝蕭嚙穀嚙踝蕭
	/**
	 * 嚙踝蕭嚙緻嚙編嚙磕嚙踝蕭嚙賡的嚙踝蕭mID
	 * @param concertid 嚙踝蕭嚙瑾嚙踝蕭嚙緣嚙諛會
	 * @param seatarea 嚙踝蕭嚙瑾嚙誕迎蕭嚙緙嚙踝蕭
	 * @param ticketamount 嚙諄來改蕭sticketstatus
	 */
	public int getSeatId(int concertid, String seatarea, int ticketamount) {

		int seatid = 0;
		String query = "";
		String execute_sql = "";
		JSONObject ticketstatus = null;
		JSONArray ticketstatusarray = new JSONArray();
		ResultSet rs = null;
		
		//STEP1 嚙踝蕭嚙篁嚙踝蕭w嚙踝蕭嚙緻嚙諉演嚙諛會嚙踝蕭ticketstatus
		try {
			conn = MysqlConnect.getConnect();
			query = "SELECT * FROM missa.concert where idconcert = ?";
			pres = conn.prepareStatement(query);
			pres.setInt(1, concertid);
			
			rs = pres.executeQuery();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
			while(rs.next()) {
				
				// STEP1.5 嚙踝蕭嚙緻ticketstatus嚙踝蕭嚙踝蕭嚙罷嚙瘠ticketstatusarray
				ticketstatus = new JSONObject(rs.getString("ticketstatus"));
				ticketstatusarray = ticketstatus.getJSONArray("data");
			}
		} catch (SQLException e) {
            /** 嚙盤嚙碼JDBC SQL嚙踝蕭嚙瞌嚙踝蕭嚙羯 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 嚙磐嚙踝蕭嚙羯嚙篁嚙盤嚙碼嚙踝蕭嚙羯嚙確嚙踝蕭 */
            e.printStackTrace();
        } finally {
            /** 嚙踝蕭嚙踝蕭嚙編嚙線嚙踝蕭嚙踝蕭嚙踝蕭狾嚙踝蕭嚙複庫嚙踝蕭嚙踝蕭嚙踝蕭嚙赭源 **/
            MysqlConnect.close( pres, conn);
        }

		//STEP2 嚙緬ticketstatus 嚙踝蕭嚙緻嚙諉區位的嚙踝蕭T
		for (int i = 0; i < ticketstatusarray.length(); i++ ) {
			JSONObject jso = ticketstatusarray.getJSONObject(i);
			String area = jso.getString("Area");//嚙踝蕭嚙緻嚙踝蕭array[i]嚙踝蕭嚙踝蕭area
			
			//STEP3 嚙踝蕭嚙踝蕭O嚙稻嚙瞑嚙緬嚙踝蕭嚙踝蕭m嚙諛同嚙璀嚙緘嚙瘦嚙諛同嚙璀嚙踝蕭嚙緻嚙稻嚙締嚙踝蕭m
			if ( area.equals(seatarea) == true ) {
				seatid = jso.getInt("Sold");
				
				//STEP4 嚙論改蕭ticketstaus嚙踝蕭嚙踝蕭sold嚙複量(嚙趣本嚙踝蕭嚙稼嚙磕嚙緻嚙踝蕭嚙磋嚙踝蕭嚙複量)
				jso.put("Sold", seatid+ticketamount);
				
			}
		}
		
		//STEP5 嚙論改蕭ticketstatus
		ticketstatus = new JSONObject();
		ticketstatus.put("data", ticketstatusarray);
		
		//STEP6 嚙踝蕭sconcert嚙踝蕭w
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
            /** 嚙盤嚙碼JDBC SQL嚙踝蕭嚙瞌嚙踝蕭嚙羯 **/
            System.err.format("SQL State: %s\n%s\n%s\n", e.getErrorCode(), e.getSQLState(), e.getMessage());
            e.printStackTrace();
		} catch (Exception e) {
            /** 嚙磐嚙踝蕭嚙羯嚙篁嚙盤嚙碼嚙踝蕭嚙羯嚙確嚙踝蕭 */
            e.printStackTrace();
        } finally {
            /** 嚙踝蕭嚙踝蕭嚙編嚙線嚙踝蕭嚙踝蕭嚙踝蕭狾嚙踝蕭嚙複庫嚙踝蕭嚙踝蕭嚙踝蕭嚙赭源 **/
            MysqlConnect.close( pres, conn);
        }

		System.out.println("finish getSeatID, return"+seatid);
		return seatid;
	}
	public int releaseSeat() {
		
		
		return 0;
	}
}
