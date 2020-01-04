package ncu.im3069.group14.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import ncu.im3069.group14.util.Token;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//進到首頁後，獲得使用者傳進來的request的所有cookie
		Cookie[] cookies = request.getCookies();
		
		String token = null;
		
		try {
			/**
		     * cookies是一個陣列，因此用for的方式看過所有的cookie name
		     * 如果這個cookie的name是token，那麼將這個cookie的value賦予給token這個字串變數
		     */
			for (Cookie c:cookies) {
				System.out.println("Name:"+c.getName());
				if(c.getName().equals("Token")) {
					token = c.getValue();
					System.out.println("Index");
					System.out.println("Incoming token: "+token);
					break;
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
		}
		
		//如果Token的value是空的或是null，代表他沒有登入，進入index.html作為首頁
		if(token==null||token.isEmpty()) {
			response.sendRedirect("index.html");
		} else {
			/** 
			 * 	如果Token的value不是空的，那就檢查這個token的值是不是可以用的
			 * Token.decode()就是檢查用的method
			 */
			try {
				Claims clmBody = Token.decode(token);
				System.out.println(clmBody.getSubject());
				//如果token被串改過，那麼這個使用者是非法的，不能讓他進到登入後的首頁
			} catch (SignatureException sigE) {
				System.out.println("The signature is invalid.");
				
				//重新給一個名叫Token的cookie
				Cookie emptyCookie = new Cookie("Token",null);
				
				//設定cookie為HttpOnly，防止cookie在前端被更改

				
				//將cookie加到response裡
				Token.addTokentoCookie(emptyCookie, response);
				
				//回傳首頁
				response.sendRedirect("index.html");
				
				//如果這個token過期了
			} catch (ExpiredJwtException expE) {
				System.out.println("The token is out of date.");
				
				//重新給一個名叫Token的cookie
				Cookie emptyCookie = new Cookie("Token",null);
				
				//設定cookie為HttpOnly，防止cookie在前端被更改

				
				//將cookie加到response裡
				Token.addTokentoCookie(emptyCookie, response);
				
				//回傳首頁
				response.sendRedirect("index.html");
			}
			
			//如果這個Token都沒有問題，則進入登入過後的首頁
			System.out.println("Is logined.");
			response.sendRedirect("index-signin.html");
			
		}
	}

}
