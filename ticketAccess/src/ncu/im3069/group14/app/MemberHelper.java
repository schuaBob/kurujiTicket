package ncu.im3069.group14.app;

import java.sql.*;
import org.json.*;
import ncu.im3069.group14.util.Mysqlconnect;
import java.text.MessageFormat;

public class MemberHelper {
	
	private MemberHelper() {
		
	}
	
	private static MemberHelper mh;
	
	private Connection con = null;
	
	private PreparedStatement pres = null;
	
	public static MemberHelper getHelper() {
		if(mh==null) {
			mh = new MemberHelper();
		}
		return mh;
	}
	
	public JSONObject create(Member m) {
		String executeSQL = "";
		long startTime = System.nanoTime();
		int row = 0;
		
		try {
			con = Mysqlconnect.getConnect();
			String query = "INSERT INTO `missa`.`member`(`name`, `email`, `password`, `idnumber`, `address`, `dateofbirth`, `phonenumber`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";
			pres = con.prepareStatement(query);
			pres.setString(1, m.getName());
			pres.setString(2, m.getEmail());
			pres.setString(3, m.getPassword());
			pres.setString(4, m.getIDNumber());
			pres.setString(5, m.getAddress());
			pres.setDate(6, m.getDOB());
			pres.setString(7, m.getPhoneNumber());
			
			row = pres.executeUpdate();
			
			executeSQL = pres.toString();
			
			System.out.println(MessageFormat.format("已執行SQL:{0}", executeSQL));
		} catch (SQLException sqlE) {
			System.err.format("SQL State: %s\n%s\n%s", sqlE.getErrorCode(),sqlE.getSQLState(),sqlE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			Mysqlconnect.close(pres, con);
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1_000_000_000;
		
		JSONObject response = new JSONObject();
		response.put("sql", executeSQL);
		response.put("time", duration);
		response.put("row", row);
		
		return response;
		
	}
	
	public JSONObject readByID(String id) {
		long startTime = System.nanoTime();
		JSONObject jsonObj = new JSONObject();
		Member m = null;
		String execSQL = "";
		ResultSet rs = null;
		try {
			con = Mysqlconnect.getConnect();
			String query = "SELECT * FROM `missa`.`member` WHERE `idmember` = ? LIMIT 1";
			pres = con.prepareStatement(query);
			pres.setInt(1,Integer.parseInt(id));
			rs = pres.executeQuery();
			execSQL = pres.toString();
			System.out.println(MessageFormat.format("已執行SQL:{0}", execSQL));
			while(rs.next()) {
				int i = rs.getInt("idmember");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				Date dob = rs.getDate("dateofbirth");
				String idn = rs.getString("idnumber");
				String phonenumber = rs.getString("phonenumber");
				String address = rs.getString("address");
				System.out.println(MessageFormat.format("id:{0},name:{1},password:{2},email:{3},dateofbirth:{4},idn:{5},phonenumber:{6},address:{7}", i, name, password, email, dob,idn,phonenumber,address));
				m = new Member(i,name,password,email,dob,idn,phonenumber,address);
				jsonObj = m.toJSONData();
			}
		} catch (SQLException sqlE) {
			sqlE.getStackTrace();
			System.err.format("SQL State: %s\n%s\n%s", sqlE.getErrorCode(),sqlE.getSQLState(),sqlE.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			Mysqlconnect.close(rs, pres,con);
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1_000_000_000;
//		JSONObject res = new JSONObject();
		System.out.println("Spend:"+duration+"sec");
//		res.put("sql", execSQL);
//		res.put("duration",duration);
//		res.put("data", jsonObj);
		return jsonObj;
	}
}
