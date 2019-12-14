package ncu.im3069.group14.util;

import java.sql.*;
import java.util.Properties;
import java.text.MessageFormat;

public class Mysqlconnect {
	
	/**
	 * JDBC(Java資料庫連線，Java Database Connectivity) Driver
	 */
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	/**
	 * DataBase connection url 
	 */
	static final String DB_URL= "jdbc:mysql://missaproject.cfojfxuhc6dx.ap-northeast-1.rds.amazonaws.com:3306";
	
	/**
	 * login user
	 */
	static final String USER = "saAdminHua";
	
	/**
	 * Password
	 */
	static final String PWD = "sadadmin2019";
	
	static {
		try {
			Class.forName(Mysqlconnect.JDBC_DRIVER);
		} catch(ClassNotFoundException cnfE) {
			System.out.println("載入JDBC驅動類別錯誤");
			System.out.println(MessageFormat.format("錯誤訊息:{0}", cnfE.getMessage()));
		} catch(Exception e) {
			System.out.println("載入JDBC驅動類別以外的錯誤");
			e.getStackTrace();
		}
	}
	
	public Mysqlconnect() {
		
	}
	
	public static Connection getConnect() {
		Properties props = new Properties();
		
		props.setProperty("useSSL", "false");
		
		props.setProperty("serverTimezone","UTC");
		
		props.setProperty("useUnicode", "true");
		
		props.setProperty("characterEncoding", "utf8");
		
		props.setProperty("user", Mysqlconnect.USER);
		
		props.setProperty("password", Mysqlconnect.PWD);
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(Mysqlconnect.DB_URL, props);
		} catch(SQLException sqlE) {
			System.out.println("與資料庫連線錯誤");
			System.out.println(MessageFormat.format("錯誤訊息:{0}", sqlE.getMessage()));
		} catch(Exception e) {
			System.out.println("與資料庫連線以外的錯誤");
			e.getStackTrace();
		}
		
		return con;
		
	}
	
	public static void close(Statement stm, Connection con) {
		try {
			if(stm!=null) {
				stm.close();
			}
			if(con!=null) {
				con.close();
			}
		} catch(SQLException sqlE) {
			System.out.println("關閉所有資料庫連線錯誤");
			System.out.println(MessageFormat.format("錯誤訊息:{0}", sqlE.getMessage()));
		} catch(Exception e) {
			System.out.println("關閉所有資料庫連線以外的錯誤");
			e.getStackTrace();
		}
	}
	
	public static void close(ResultSet rs, Statement stm, Connection con) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stm != null) {
				stm.close();
			}
			if(con!=null) {
				con.close();
			}
		} catch(SQLException sqlE) {
			System.out.println("關閉所有資料庫連線錯誤");
			System.out.println(MessageFormat.format("錯誤訊息:{0}", sqlE.getMessage()));
		} catch (Exception e) {
			System.out.println("關閉所有資料庫連線以外的錯誤");
			e.getStackTrace();
		}
	}
//	public static String[] stringToArray(String data, String delimiter) {
//      String[] result;
//      result = data.split(delimiter);
//      return result;
//	}
}
