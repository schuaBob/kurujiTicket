package ncu.im3069.group14.app;

import java.util.Date;

public class Member {
	private int id;
	private String name;
	private String password;
	private String email;
	private Date dob;
	private String idnumber;
	private String phonenumber;
	private String address;
	
	public Member(String name,String password, String email, Date dob, String idnumber, String phonenumber, String address) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.phonenumber = phonenumber;
		this.idnumber = idnumber;
		this.address = address;
	}
	
	public int getID() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getPassword() {
		return this.password;
	}
	
	public String getEmail() {
		return this.email;
	}
	public Date getDOB() {
		return this.dob;
	}
	public String getIDNumber() {
		return this.idnumber;
	}
	public String getPhoneNumber() {
		return  this.phonenumber;
	}
	public String getAddress() {
		return this.address;
	}
}
