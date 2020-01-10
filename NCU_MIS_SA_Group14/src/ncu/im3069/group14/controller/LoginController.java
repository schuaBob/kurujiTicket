package ncu.im3069.group14.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import ncu.im3069.group14.app.MemberHelper;
import ncu.im3069.group14.tools.RequestHandler;
import ncu.im3069.group14.util.Token;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberHelper mh = MemberHelper.getHelper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Login page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				if(c.getName().equals("Token")) {
					System.out.println(c.getValue());
					break;
				}
			}
		} else {
			System.out.println("no cookie");
		}
		response.sendRedirect("signin.html");
	}

	/**
	 * Login POST
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//先將註冊的request傳到requesthandler，接著轉成JSONObject 的型態
		RequestHandler rh = new RequestHandler(request);
		JSONObject reqObj = rh.toJsonObj();
		
		//取得acccount跟password的欄位
		String account = reqObj.getString("account");
		String password = reqObj.getString("password");
		
		//驗帳帳號密碼是否正確，會回傳一個id
		int id = mh.checkPassword(account, password);
		
		//如果id不等於0代表有這組會員資料
		if(id!=0) {
			
			//有資料的話就根據這個人的會員id產生Token
			String jwt = Token.createToken(String.valueOf(id));
			
			//產生Token後加到Cookie裡
			Cookie jwtCookie = new Cookie("Token",jwt);
			
			//將這個cookie設成httpOnly
			jwtCookie.setHttpOnly(true);
			
			//這個cookie最久可以存在1hour(60sec x 60min)
			jwtCookie.setMaxAge(60*60);
			
			//將cookie加到response裡
			response.addCookie(jwtCookie);
				
			//回到首頁
			JSONObject resObj = new JSONObject();
			resObj.put("message", "Login successfully.");
			resObj.put("redirect", "/");
			rh.sendJsonRes(resObj, response);
		} else {
			
			//密碼輸入錯誤或沒有這組會員資料的話
			JSONObject resObj = new JSONObject();
			resObj.put("message", "Login fail");
			rh.sendJsonErr(resObj, response);
		}
	}
	
	//Logout
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestHandler rh = new RequestHandler(request);
		//將Token的cookie值刪掉
		Cookie jwtCookie = new Cookie("Token",null);
		
		//設cookie為httpOnly
		jwtCookie.setHttpOnly(true);
		
		//將cookie加到response裡
		response.addCookie(jwtCookie);
		
		//回到首頁
		JSONObject resObj = new JSONObject();
		resObj.put("message", "Logout successfully.");
		resObj.put("redirect", "/");
		rh.sendJsonRes(resObj, response);
	}

}
