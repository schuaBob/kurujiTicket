package ncu.im3069.group14.app;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Order {
	
	private int id; //�q��s���A��sql�۰ʲ���
	private int memberid; //FK�A�����O�ѭ��ӷ|���U���q��
	private String payment; //�����o���q��n�έ��@�ؤ覡�I��
	private boolean paid; //�I�ڤF�S?�쥻�OFalse�A�I�ګ��ܦ�True
	private int ticketamount; //�q���`��(1~4)
	private Timestamp createtime; //�q��إ߮ɶ�
	
	
	/**
	 * �إ߭q��A�ݭn�H�U3�����
	 * @param memberid
	 * @param payment
	 * @param ticketamount
	 */
	public Order(int memberid, String payment, int ticketamount) {
		this.memberid = memberid;
		this.payment = payment;
		this.paid = false ; //��إ߭q��@�w�٨S�I��
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
