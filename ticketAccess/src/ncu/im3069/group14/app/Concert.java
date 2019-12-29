package ncu.im3069.group14.app;

import org.json.*;
import java.util.*;

public class Concert {
	//name �t�۷|�W��
	private String name;
	//id �t�۷|�s��
	private int id;
	//supplier id �����ӽs��
	private int supplierId;
	//location �t�۷|�a�}
	private String location;
	//picture �t�۷|�Ϥ�(���|)
	private String picture;
	//seatpicture �y���(���|)
	private String seatpicture;
	//endsellingtime �t�۷|�Ⲽ�I��ɶ�
	private String endsellingtime;
	//content �t�۷|���e
	private String content;
	//ticketstatus �U���ϲ����H���`�q
	private JSONObject ticketstatus;
	//concertstarttime �t�۷|�}�l�ɶ�
	private String concertstarttime;
	//concertendtime �t�۷|�}�l�ɶ�
	private String concertendtime;
	
	private ConcertHelper ch = ConcertHelper.getHelper();
	
	public Concert(String name) {		
		this.name = name;
//		update();
	}
	public Concert(String name,int supplierId, String location, String picture, String seatpicture, String endsellingtime
			, String content, JSONObject ticketstatus, String concertstarttime,String concertendtime) {
		this.name = name;
		this.supplierId = supplierId;
		this.location = location;
		this.picture = picture;
		this.seatpicture = seatpicture;
		this.endsellingtime = endsellingtime;
		this.content = content;
		this.ticketstatus = ticketstatus;
		this.concertstarttime = concertstarttime;
		this.concertendtime = concertendtime;
	}
	public Concert(JSONObject obj) {
		this.name = obj.getString("concertName");
		this.supplierId = obj.getInt("supplierId");
		this.location = obj.getString("location");
		this.picture = obj.getString("picture");
		this.seatpicture = obj.getString("seatpicture");
		this.endsellingtime = obj.getString("endsellingtime");
		this.content = obj.getString("content");
		this.ticketstatus = obj.getJSONObject("ticketstatus");
		this.concertstarttime = obj.getString("concertstarttime");
		this.concertendtime = obj.getString("concertendtime");
	}
	public String getConcertName() {
		return name;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public String getLocation() {
		return location;
	}
	public String getPicture() {
		return picture;
	}
	public String getSeatPicture() {
		return seatpicture;
	}
	public String getEndSellingTime() {
		return endsellingtime;
	}
	public String getContent() {
		return content;
	}
	public JSONObject getTicketStatus() {
		return ticketstatus;
	}
	public String getConcertStartTime() {
		return concertstarttime;
	}
	public String getConcertEndTime() {
		return concertendtime;
	}
	
//	public JSONObject update() {
//		/** �s�ؤ@��JSONObject�ΥH�x�s��s�ᤧ��� */
//        JSONObject data = new JSONObject();
//        
//	}
}
