package ncu.im3069.group14.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		response.sendRedirect("login.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Login action
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh = new RequestHandler(request);
		JSONObject jsonObj = new JSONObject();
		String account = rh.getParameter("account");
		String password = rh.getParameter("password");
		int id = mh.checkPassword(account, password);
		if(id!=0) {
			String jwt = Token.createToken(String.valueOf(id));
			response.setHeader("Authorization", "Bearer " + jwt);
			jsonObj.put("message", "�n�J���\");
			rh.sendJsonRes(jsonObj, response);
		} else {
			jsonObj.put("message", "�n�J����");
			rh.sendJsonErr(jsonObj, response);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestHandler rh = new RequestHandler(request);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("message","���\�n�X");
		response.setHeader("Authorization", null);
		rh.sendJsonRes(jsonObj, response);
	}

}
