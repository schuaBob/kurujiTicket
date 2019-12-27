package ncu.im3069.group14.app;


import java.time.LocalDateTime;
import org.json.JSONObject;
import java.sql.Timestamp;

public class Order {
	
	private int idorder; //�q��s���A��sql�۰ʲ���
	private int memberid; //FK�A�����O�ѭ��ӷ|���U���q��
	private String payment; //�����o���q��n�έ��@�ؤ覡�I��
	private boolean paid; //�I�ڤF�S?�쥻�OFalse�A�I�ګ��ܦ�True
	private int ticketamount; //�q���`��(1~4)
	private Timestamp createtime; //�q��إ߮ɶ� YYYY-MM-DD HH:MM:SS
	private int concertid;
	private int totalprice;
	
	/**
	 * �إ߭q��A�ݭn�H�U3�����
	 * @param memberid
	 * @param payment
	 * @param ticketamount
	 */
	public Order(int memberid, String payment, int ticketamount, int concertid, int totalprice) {
		//Date date = new Date();
		//SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		
		this.memberid = memberid;
		this.payment = payment; //credit, line, 7
		this.paid = false ; //��إ߭q��@�w�٨S�I��
		this.ticketamount = ticketamount;
		this.createtime = Timestamp.valueOf(LocalDateTime.now());;
		this.concertid = concertid;
		this.totalprice = totalprice;
		System.out.println(createtime);
	}
	/**
	 * �Ф@�ӥ��s��order�A�]�t�Ҧ�order����T�A�ΦbgetAllOrder�̭�
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
