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
			
			System.out.println(MessageFormat.format("已執行SQL指令:{0}", executeSQL));
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
			pres.setString(1, id);
			rs = pres.executeQuery();
			execSQL = pres.toString();
			System.out.println(MessageFormat.format("已執行SQL:{0}", execSQL));
			
			m = new Member(rs.getInt("idmember"),rs.getString("name"),rs.getString("password"),rs.getString("email"),rs.getDate("dateofbirth"),rs.getString("idnumber"),rs.getString("phonenumber"),rs.getString("address"));
			jsonObj = m.toJSONData();
		} catch (SQLException sqlE) {
			System.err.format("SQL State: %s\n%s\n%s", sqlE.getErrorCode(),sqlE.getSQLState(),sqlE.getMessage());
			
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			Mysqlconnect.close(rs, pres,con);
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1_000_000_000;
		JSONObject res = new JSONObject();
		
		res.put("sql", execSQL);
		res.put("row", 1);
		res.put("time", duration);
		res.put("data", jsonObj);
		return res;
	}
}
