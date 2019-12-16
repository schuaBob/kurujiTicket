package ncu.im3069.group14.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ncu.im3069.group14.app.*;
import java.sql.Date;
import org.json.*;
import ncu.im3069.group14.tools.RequestHandler;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/member")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MemberHelper mh = MemberHelper.getHelper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        System.out.println("after super()");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestHandler rh = new RequestHandler(request);
		
		String id = rh.getParameter("id");
		if(!id.isEmpty()) {
			JSONObject data = mh.readByID(id);
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("message", "Query success.");
			jsonObj.put("data", data);
			rh.sendJsonRes(jsonObj, response);
			
		} else {
			JSONObject data = mh.readByID("5");
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("message", "Query success.");
			jsonObj.put("data", data);
			rh.sendJsonRes(jsonObj, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestHandler rh = new RequestHandler(request);
		JSONObject req = rh.toJsonObj();
		
		Date dob = Date.valueOf(req.getString("dob"));
		String name = req.getString("name");
		String password = req.getString("password");
		String email = req.getString("email");
		String idn= req.getString("idnumber");
		String phonenumber = req.getString("phonenumber");
		String address = req.getString("address");
		Member m = new Member(name,password,email,dob,idn,phonenumber,address);
		JSONObject jsonObj = new JSONObject();
		
		if(mh.isExist(m)) {
			jsonObj.put("message", "資料已被使用。");
			rh.sendJsonErr(jsonObj, response);
		}else {
			JSONObject res = mh.create(m);
			jsonObj.put("message", "註冊成功。");
			jsonObj.put("res",res);
			rh.sendJsonRes(res, response);
		}
	}

}
