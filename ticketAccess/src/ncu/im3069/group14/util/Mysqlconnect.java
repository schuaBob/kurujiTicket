package ncu.im3069.group14.util;

import java.sql.*;
import java.util.Properties;
import java.text.MessageFormat;

public class Mysqlconnect {
	
	/**
	 * JDBC(Java��Ʈw�s�u�AJava Database Connectivity) Driver
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
			System.out.println("JDBC Driver Class Error");
			System.out.println(MessageFormat.format("Error message:{0}", cnfE.getMessage()));
		} catch(Exception e) {
			System.out.println("Something about JDBC Driver has Error");
			e.getStackTrace();
		}
	}
	
	public Mysqlconnect() {
		
	}
	
	public static Connection getConnect() {
		Properties props = new Properties();
		
		props.setProperty("useSSL", "false");
		
		props.setProperty("serverTimezone","Asia/Shanghai");
		
		props.setProperty("useUnicode", "true");
		
		props.setProperty("characterEncoding", "utf8");
		
		props.setProperty("user", Mysqlconnect.USER);
		
		props.setProperty("password", Mysqlconnect.PWD);
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(Mysqlconnect.DB_URL, props);
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
			System.out.println("�����Ҧ���Ʈw�s�u���~");
			System.out.println(MessageFormat.format("���~�T��:{0}", sqlE.getMessage()));
		} catch (Exception e) {
			System.out.println("�����Ҧ���Ʈw�s�u�H�~�����~");
			e.getStackTrace();
		}
	}
//	public static String[] stringToArray(String data, String delimiter) {
//      String[] result;
//      result = data.split(delimiter);
//      return result;
//	}
}
