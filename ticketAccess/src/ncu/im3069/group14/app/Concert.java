package ncu.im3069.group14.app;

import org.json.*;
import java.util.*;

public class Concert {
	//name 演唱會名稱
	private String name;
	//id 演唱會id
	private int id;
	//supplier id 供應商id
	private int supplierId;
	//location 演唱會地點
	private String location;
	//picture 演唱會圖片
	private String picture;
	//seatpicture 座位圖
	private String seatpicture;
	//endsellingtime 銷售結束時間
	private String endsellingtime;
	//content 演唱會內容
	private String content;
	//ticketstatus 記錄各種票券資訊
	private JSONObject ticketstatus;
	//concertstarttime 演唱會開始時間
	private String concertstarttime;
	//concertendtime 演唱會結束時間
	private String concertendtime;
	private String session;
	private ConcertHelper ch = ConcertHelper.getHelper();
	
	public Concert(String name) {		
		this.name = name;
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
		if(obj.has("concertId")) {
			this.id = obj.getInt("concertId");
		}
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
		this.session = obj.getString("session");
	}
	public String getSession() {
		return session;
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
}
