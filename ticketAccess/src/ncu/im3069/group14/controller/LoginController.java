package ncu.im3069.group14.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Login action
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh = new RequestHandler(request);
		JSONObject reqObj = rh.toJsonObj();
		
		JSONObject jsonObj = new JSONObject();
		
		String account = reqObj.getString("account");
		String password = reqObj.getString("password");
		
		int id = mh.checkPassword(account, password);
		if(id!=0) {
			String jwt = Token.createToken(String.valueOf(id));
			Cookie jwtCookie = new Cookie("Token",jwt);
			jwtCookie.setHttpOnly(true);
			jwtCookie.setMaxAge(60*60);
			response.addCookie(jwtCookie);
			response.setHeader("Authorization", "Bearer " + jwt);
			jsonObj.put("message", "Login success");
			RequestDispatcher rd = request.getRequestDispatcher("/");
			rd.forward(request, response);
		} else {
			jsonObj.put("message", "Login fail");
			rh.sendJsonErr(jsonObj, response);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestHandler rh = new RequestHandler(request);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("message","¦¨¥\µn¥X");
		response.setHeader("Authorization", null);
		rh.sendJsonRes(jsonObj, response);
	}

}
