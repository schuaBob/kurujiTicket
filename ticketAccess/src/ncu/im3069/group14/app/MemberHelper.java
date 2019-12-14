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
			
			System.out.println(MessageFormat.format("¤w°õ¦æSQL«ü¥O:{0}", executeSQL));
		} catch (SQLException sqlE) {
			System.err.format("SQL State: %s\n%s\n%s", sqlE.getErrorCode(),sqlE.getSQLState(),sqlE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			Mysqlconnect.close(pres, con);
		}
		
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		
		JSONObject response = new JSONObject();
		response.put("sql", executeSQL);
		response.put("time", duration);
		response.put("row", row);
		
		return response;
		
	}

}
