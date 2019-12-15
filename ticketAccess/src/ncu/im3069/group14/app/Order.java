package ncu.im3069.group14.app;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Order {
	
	private int id; //訂單編號，由sql自動產生
	private int memberid; //FK，紀錄是由哪個會員下的訂單
	private String payment; //紀錄這筆訂單要用哪一種方式付款
	private boolean paid; //付款了沒?原本是False，付款後變成True
	private int ticketamount; //訂單總數(1~4)
	private Timestamp createtime; //訂單建立時間
	
	
	/**
	 * 建立訂單，需要以下3筆資料
	 * @param memberid
	 * @param payment
	 * @param ticketamount
	 */
	public Order(int memberid, String payment, int ticketamount) {
		this.memberid = memberid;
		this.payment = payment;
		this.paid = false ; //剛建立訂單一定還沒付錢
		this.ticketamount = ticketamount;
		this.createtime = Timestamp.valueOf(LocalDateTime.now());	
		System.out.println(createtime);
	}
	
	public int getId() {
		return this.id;
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
	
	public boolean isPaid() {
		this.paid = true;
		return this.paid;
	}
	
}
