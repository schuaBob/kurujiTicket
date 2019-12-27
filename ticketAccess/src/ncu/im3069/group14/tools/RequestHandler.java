package ncu.im3069.group14.tools;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

import org.json.*;
import ncu.im3069.group14.util.Token;

import javax.servlet.http.*;
public class RequestHandler {
	private HttpServletRequest req;
	
//	private String reqStr;
	
	public RequestHandler(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		this.req = request;
//		String s = null;
//		StringBuilder sb = new StringBuilder();
		
		
//		while((s = request.getReader().readLine())!=null) {
//			System.out.println(s);
//			sb.append(s);
//		}
//		this.reqStr = sb.toString();
//		System.out.println("[@RequestHandler]"+this.reqStr);
	}
	
	public String getParameter(String param) {
		if(this.req.getParameter(param)!= null) {
			return this.req.getParameter(param);
		} else {
			return "";	
		}
	}
	
	public JSONObject toJsonObj() {
		
		String str,wholestr="";
		try {
			BufferedReader bf = req.getReader();			
			while((str = bf.readLine()) != null) {
				wholestr += str;
			}
		}catch(IOException ioexception) {
			System.err.println("¿ù!!!!");
		}
		System.out.println(wholestr);
		JSONObject temp = new JSONObject(wholestr);
		return temp;
		
		
		
//		Map<String, String[]> params = this.req.getParameterMap();
//
//		Iterator<String> i = params.keySet().iterator();
//		while ( i.hasNext() ){
//			count++;
//			String key = (String) i.next();
//			System.out.println(key);
//			String value = ((String[]) params.get( key ))[ 0 ];
////			System.out.println(value);
//			temp.put(key, value);
//
//		}
		
	}
	
	public void sendJsonRes(JSONObject data,HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);
		out.print(data);
		out.flush();
	}
	
	public void sendJsonErr(JSONObject data, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("applicatiion/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(400);
		out.print(data);
		out.flush();
	}
	public String getMemberIDinToken() {
		String authorization = this.req.getHeader("Authorization");
		String token = authorization.split(" ")[1];
		String id = null;
		try {
			id = Token.decode(token).getSubject();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return id;
	}
}
