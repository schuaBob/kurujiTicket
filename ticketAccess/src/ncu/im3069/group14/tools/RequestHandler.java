package ncu.im3069.group14.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.*;
public class RequestHandler {
	private HttpServletRequest req;
	
	private String reqStr;
	
	public RequestHandler(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		this.req = request;
		String s = null;
		StringBuilder sb = new StringBuilder();
		
		while((s = request.getReader().readLine())!=null) {
			sb.append(s);
		}
		this.reqStr = sb.toString();
		System.out.println("[@RequestHandler]"+this.reqStr);
	}
	
	public String getParameter(String param) {
		if(this.req.getParameter(param)!= null) {
			return this.req.getParameter(param);
		} else {
			return "";			
		}
	}
}
