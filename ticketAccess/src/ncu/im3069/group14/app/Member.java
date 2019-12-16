package ncu.im3069.group14.app;

import java.sql.Date;
import org.json.*;

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
	
	public Member(int id, String name,String password, String email, Date dob, String idnumber, String phonenumber, String address) {
		this.id = id;
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
	
	public JSONObject toJsonData() {
		JSONObject jTemp = new JSONObject();
		jTemp.put("id", getID());
		jTemp.put("name", getName());
		jTemp.put("password", getPassword());
		jTemp.put("email", getEmail());
		jTemp.put("dateofbirth", getDOB());
		jTemp.put("idnumber", getIDNumber());
		jTemp.put("phonenumber", getPhoneNumber());
		jTemp.put("address", getAddress());
		return jTemp;
	}
}
