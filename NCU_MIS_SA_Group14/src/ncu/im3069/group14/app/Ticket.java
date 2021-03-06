package ncu.im3069.group14.app;

import org.json.JSONObject;

public class Ticket {
	private int idticket;
	private int concertid;
	private int orderid;
	private String seatarea;
	private int seatid;
	private boolean isused;
	
	private String email;
	private String phonenumber;
	private String name;
	
	/**
	 * 建立一張新票券
	 * @isused 一定還沒用
	 */
	public Ticket(int concertid, int orderid, String seatarea, int seatid, String email, String phonenumber, String name) {
		this.concertid = concertid;
		this.orderid = orderid;
		this.seatarea = seatarea;
		this.seatid = seatid;
		this.isused = false;
		this.email = email;
		this.phonenumber = phonenumber;
		this.name = name;
	}
	/**
	 * 用來查詢票券
	 */
	public Ticket(int idticket, int concertid, int orderid, String seatarea, int seatid, boolean isused, String email, String phonenumber, String name) {
		this.idticket = idticket;
		this.concertid = concertid;
		this.orderid = orderid;
		this.seatarea = seatarea;
		this.seatid = seatid;
		this.isused = isused;
		this.email = email;
		this.phonenumber = phonenumber;
		this.name = name;
	}
	public int getIdticket() {
		return this.idticket;
	}	
	public int getConcertid() {
		return this.concertid;
	}
	public int getOrderid() {
		return this.orderid;
	}
	public String getSeatarea() {
		return this.seatarea;
	}
	public int getSeatid() {
		return this.seatid;
	}
	public boolean getIsused() {
		return this.isused;
	}
	public String getEmail() {
		return this.email;
	}
	public String getPhonenumber() {
		return this.phonenumber;
	}
	public String getName() {
		return this.name;
	}
	public void setSeatid(int seatid) {
		this.seatid = seatid;
		return;
	}
	public void setIdticket(int idticket) {
		this.idticket = idticket;
		return;
	}
	public void UsedTicket(int idticket) {
		this.isused = true;
		return;
	}
	public JSONObject toJsonData(int idticket) {
		setIdticket(idticket);
		JSONObject jTemp = new JSONObject();
		jTemp.put("idticket", getIdticket());
		jTemp.put("concertid", getConcertid());
		jTemp.put("orderid", getOrderid());
		jTemp.put("seatarea", getSeatarea());
		jTemp.put("seatid", getSeatid());
		jTemp.put("idused", getIsused());
		jTemp.put("name", getName());
		jTemp.put("phonenumber", getPhonenumber());
		jTemp.put("email", getEmail());
		
		return jTemp;
	}
	
}
