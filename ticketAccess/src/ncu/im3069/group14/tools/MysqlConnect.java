package ncu.im3069.group14.util;

import java.sql.*;
import java.util.Properties;
import java.text.MessageFormat;

public class MysqlConnect {
	
	/**
	 * JDBC(Java Database Connectivity) Driver
	 */
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	/**
	 * DataBase connection url 
	 */
	static final String DB_URL= "jdbc:mysql://missaproject.cfojfxuhc6dx.ap-northeast-1.rds.amazonaws.com:3306/missa";
	
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
			Class.forName(MysqlConnect.JDBC_DRIVER);
		} catch(ClassNotFoundException cnfE) {
			System.out.println("JDBC Driver Class Error");
			System.out.println(MessageFormat.format("Error message:{0}", cnfE.getMessage()));
		} catch(Exception e) {
			System.out.println("Something about JDBC Driver has Error");
			e.getStackTrace();
		}
	}
	
	public MysqlConnect() {
		
	}
	
	public static Connection getConnect() {
		Properties props = new Properties();
		
		props.setProperty("useSSL", "false");
		
		props.setProperty("serverTimezone","Asia/Shanghai");
		
		props.setProperty("useUnicode", "true");
		
		props.setProperty("characterEncoding", "utf8");
		
		props.setProperty("user", MysqlConnect.USER);
		
		props.setProperty("password", MysqlConnect.PWD);
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(MysqlConnect.DB_URL, props);
		} catch(SQLException sqlE) {
			System.out.println("Connection Error");
			System.out.println(MessageFormat.format("Error Mesage:{0}", sqlE.getMessage()));
		} catch(Exception e) {
			System.out.println("Something about Connection has Error");
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
			System.out.println("Close Connection Error");
			System.out.println(MessageFormat.format("Error Code:{0}", sqlE.getMessage()));
		} catch(Exception e) {
			System.out.println("Something about close connection has error");
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
			System.out.println("Connection Close Error");
			System.out.println(MessageFormat.format("Error Message:{0}", sqlE.getMessage()));
		} catch (Exception e) {
			System.out.println("Connection Close has Error");
			e.getStackTrace();
		}
	}
//	public static String[] stringToArray(String data, String delimiter) {
//      String[] result;
//      result = data.split(delimiter);
//      return result;
//	}
}
