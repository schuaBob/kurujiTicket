package ncu.im3069.group14.app;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConcertHelper {
	
	private ConcertHelper() {
		
	}
	
	private static ConcertHelper ch;
	
	private Connection conn = null;	
	private PreparedStatement pres = null;
	
	public static ConcertHelper getHelper() {
		if(ch==null) {
			ch = new ConcertHelper();
		}
		return ch;
	}
	
	public Boolean checkDuplicate(Supplier s) {
		
	}
	
	public JSONObject getBySupplierId(Integer id) {
		
	}
}
