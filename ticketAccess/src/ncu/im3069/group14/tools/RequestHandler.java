package ncu.im3069.group14.tools;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

import org.json.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import ncu.im3069.group14.util.Token;

import javax.servlet.http.*;
import javax.servlet.http.Cookie;
public class RequestHandler {
	private HttpServletRequest req;
	
//	private String reqStr;
	
	public RequestHandler(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		this.req = request;
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
			ioexception.getStackTrace();
		}
		System.out.println(wholestr);
		JSONObject temp = new JSONObject(wholestr);
		return temp;		
	}
	
	public void sendJsonRes(JSONObject data,HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
	}
	
	public void sendJsonErr(JSONObject data, HttpServletResponse response) throws IOException {
		response.setContentType("applicatiion/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(400);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
	}
	public String getMemberIDinRequest() {
		
		Cookie[] cookies = this.req.getCookies();
			
		String token = null;
		
		for (Cookie c:cookies) {
			System.out.println("Name:"+c.getName());
			if(c.getName().equals("Token")) {
				token = c.getValue();
				System.out.println("Incoming token: "+token);
				break;
			}
		}
		Claims claimBody = null;
		try {
			claimBody = Token.decode(token);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "0";
		}
		return claimBody.getSubject();
		
	}
}
