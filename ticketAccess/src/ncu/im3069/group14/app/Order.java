package ncu.im3069.group14.app;


import java.time.LocalDateTime;
import org.json.JSONObject;
import java.sql.Timestamp;

public class Order {
	
	private int idorder; //訂單編號，由sql自動產生
	private int memberid; //FK，紀錄是由哪個會員下的訂單
	private String payment; //紀錄這筆訂單要用哪一種方式付款
	private boolean paid; //付款了沒?原本是False，付款後變成True
	private int ticketamount; //訂單總數(1~4)
	private Timestamp createtime; //訂單建立時間 YYYY-MM-DD HH:MM:SS
	private int concertid;
	private int totalprice;
	
	/**
	 * 建立訂單，需要以下3筆資料
	 * @param memberid
	 * @param payment
	 * @param ticketamount
	 */
	public Order(int memberid, String payment, int ticketamount, int concertid, int totalprice) {
		//Date date = new Date();
		//SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		
		this.memberid = memberid;
		this.payment = payment; //credit, line, 7
		this.paid = false ; //剛建立訂單一定還沒付錢
		this.ticketamount = ticketamount;
		this.createtime = Timestamp.valueOf(LocalDateTime.now());;
		this.concertid = concertid;
		this.totalprice = totalprice;
		System.out.println(createtime);
	}
	/**
	 * 創一個全新的order，包含所有order的資訊，用在getAllOrder裡面
	 */
	public Order(int idorder, int memberid, String payment, boolean paid, int ticketamount, Timestamp createtime, int concertid, int totalprice) {
		this.idorder = idorder;
		this.memberid = memberid;
		this.payment = payment; //credit, line, 7-11
		this.paid = paid ; 
		this.ticketamount = ticketamount;
		this.createtime = createtime;
		this.concertid = concertid;
		this.totalprice = totalprice;
	}
	
	public int getIdorder() {
		return this.idorder;
	}
	
	public int getMemberid() {
		return this.memberid;
	}
	
	public String getPayment() {
		return this.payment;
	}
	
	public boolean getPaid() {
		return this.paid;
	}
	
	public int getTicketamount() {
		return this.ticketamount;
	}
	
	public Timestamp getCreatetime() {
		return this.createtime;
	}
	
	public int getConcertid() {
		return this.concertid;
	}
	public int getTotalprice() {
		return this.totalprice;
	}
	public boolean isPaid() {
		this.paid = true;
		return this.paid;
	}
	public void updateAmount(int ticketamount) {
		this.ticketamount = ticketamount;
		return;
	}
	public void setIdorder(int idorder) {
		this.idorder = idorder;
		return;
	}
	public JSONObject toJsonData(int idorder) {
		this.idorder = idorder;
		JSONObject jTemp = new JSONObject();
		jTemp.put("idorder", getIdorder());
		jTemp.put("memberid", getMemberid());
		jTemp.put("payment", getPayment());
		jTemp.put("paid", getPaid());
		jTemp.put("ticketamount", getTicketamount());
		jTemp.put("createtime", getCreatetime());
		jTemp.put("concertid", getConcertid());
		jTemp.put("totalprie", getTotalprice());
		return jTemp;
	}
	
}
