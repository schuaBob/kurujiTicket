package ncu.im3069.group14.app;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.text.*;

public class Order {
	
	private int id; //訂單編號，由sql自動產生
	private int memberid; //FK，紀錄是由哪個會員下的訂單
	private String payment; //紀錄這筆訂單要用哪一種方式付款
	private boolean paid; //付款了沒?原本是False，付款後變成True
	private int ticketamount; //訂單總數(1~4)
	private Date createtime; //訂單建立時間 YYYY-MM-DD HH:MM:SS
	
	
	/**
	 * 建立訂單，需要以下3筆資料
	 * @param memberid
	 * @param payment
	 * @param ticketamount
	 */
	public Order(int memberid, String payment, int ticketamount) {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		
		this.memberid = memberid;
		this.payment = payment; //credit, line, 7
		this.paid = false ; //剛建立訂單一定還沒付錢
		this.ticketamount = ticketamount;
		this.createtime = date;
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
	
	public Date getCreatetime() {
		return this.createtime;
	}
	
	public boolean isPaid() {
		this.paid = true;
		return this.paid;
	}
	
}
