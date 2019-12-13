package ncu.im3069.group14.app;

import java.sql.*;
import org.json.*;

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
	
//	public JSONObject create(Member m) {
//		String ecexcute_sql = "";
//		long start_time = System.nanoTime();
//		int row = 0; 
//	}
	

}
