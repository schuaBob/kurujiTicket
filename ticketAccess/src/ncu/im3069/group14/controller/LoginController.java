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
		
		//���N���U��request�Ǩ�requesthandler�A�����নJSONObject �����A
		RequestHandler rh = new RequestHandler(request);
		JSONObject reqObj = rh.toJsonObj();
		
		//���oacccount��password�����
		String account = reqObj.getString("account");
		String password = reqObj.getString("password");
		
		//��b�b���K�X�O�_���T�A�|�^�Ǥ@��id
		int id = mh.checkPassword(account, password);
		
		//�p�Gid������0�N���o�շ|�����
		if(id!=0) {
			
			//����ƪ��ܴN�ھڳo�ӤH���|��id����Token
			String jwt = Token.createToken(String.valueOf(id));
			
			//����Token��[��Cookie��
			Cookie jwtCookie = new Cookie("Token",jwt);
			
			//�N�o��cookie�]��httpOnly
			jwtCookie.setHttpOnly(true);
			
			//�o��cookie�̤[�i�H�s�b1hour(60sec x 60min)
			jwtCookie.setMaxAge(60*60);
			
			//�Ncookie�[��response��
			response.addCookie(jwtCookie);
				
			//�^�쭺��
			JSONObject resObj = new JSONObject();
			resObj.put("message", "Login successfully.");
			resObj.put("redirect", "/");
			rh.sendJsonRes(resObj, response);
		} else {
			
			//�K�X��J���~�ΨS���o�շ|����ƪ���
			JSONObject resObj = new JSONObject();
			resObj.put("message", "Login fail");
			rh.sendJsonErr(resObj, response);
		}
	}
	
	//Logout
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestHandler rh = new RequestHandler(request);
		//�NToken��cookie�ȧR��
		Cookie jwtCookie = new Cookie("Token",null);
		
		//�]cookie��httpOnly
		jwtCookie.setHttpOnly(true);
		
		//�Ncookie�[��response��
		response.addCookie(jwtCookie);
		
		//�^�쭺��
		JSONObject resObj = new JSONObject();
		resObj.put("message", "Logout successfully.");
		resObj.put("redirect", "/");
		rh.sendJsonRes(resObj, response);
	}

}
