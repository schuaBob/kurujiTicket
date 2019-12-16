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
//		long startTime = System.nanoTime();
		boolean b = false;
		
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
			
			b = pres.execute();
			
			executeSQL = pres.toString();
			
			System.out.println(MessageFormat.format("已執行SQL:{0}", executeSQL));
		} catch (SQLException sqlE) {
			System.err.format("SQL State: %s\n%s\n%s", sqlE.getErrorCode(),sqlE.getSQLState(),sqlE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			Mysqlconnect.close(pres, con);
		}
		JSONObject data = new JSONObject();
		JSONObject response = new JSONObject();
		data.put("name", m.getName());
		data.put("email", m.getEmail());
		data.put("password", m.getPassword());
		data.put("idnumber", m.getIDNumber());
		data.put("address", m.getAddress());
		data.put("dob", m.getDOB());
		data.put("phonenumber", m.getPhoneNumber());
		response.put("Insert", b);
		response.put("data", data);
		return response;
//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime)/1_000_000_000;
//		
//		response.put("sql", executeSQL);
//		response.put("time", duration);
//		response.put("row", row);
		
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
				jsonObj = m.toJsonData();
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
	
	public boolean isExist(Member m) {
		ResultSet result = null;
		int row = -1;
		try {
			con = Mysqlconnect.getConnect();
			String sql = "Select count(*) FROM `missa`.`member` where idnumber = ? or email = ? or phonenumber = ?";
			String email = m.getEmail();
			String idnumber = m.getIDNumber();
			String phonenumber =  m.getPhoneNumber();
			pres = con.prepareStatement(sql);
			pres.setString(1, email);
			pres.setString(2, idnumber);
			pres.setString(3,phonenumber);
			
			result = pres.executeQuery();
			result.next();
			
			row = result.getInt("count(*)");
			System.out.println(MessageFormat.format("{0} rows have the same column data.", row));
			
		} catch(SQLException sqlE) {
			System.err.format("SQL State: %s\n%s\n%s",sqlE.getErrorCode(),sqlE.getSQLState(),sqlE.getErrorCode());
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			Mysqlconnect.close(result, pres,con);
		}
		
		return (row == 0) ? false : true;
		
	}
}
