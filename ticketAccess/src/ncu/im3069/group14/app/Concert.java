package ncu.im3069.group14.app;

import org.json.*;
import java.util.Calendar;

public class Concert {
	
	private String concertName;
	private int id;
	
	private ConcertHelper ch = ConcertHelper.getHelper();
	
	public Concert(String name) {		
		this.concertName = name;
//		update();
	}
	public String getConcertName() {
		return concertName;
	}
	
//	public JSONObject update() {
//		/** �s�ؤ@��JSONObject�ΥH�x�s��s�ᤧ��� */
//        JSONObject data = new JSONObject();
//        
//	}
}
