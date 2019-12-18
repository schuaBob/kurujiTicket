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
			jsonObj.put("message", "鞈�歇鋡思蝙���");
			rh.sendJsonErr(jsonObj, response);
		}else {
			JSONObject res = mh.create(m);
			if(res.getInt("row")>0) {
				jsonObj.put("message", "閮餃�����");
				jsonObj.put("res",res);
				rh.sendJsonRes(res, response);
			} else {
				jsonObj.put("message", "閮餃�仃����");
				jsonObj.put("res",res);
				rh.sendJsonErr(res, response);
			}
			
		}
	}
	
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestHandler rh = new RequestHandler(request);
		JSONObject req = rh.toJsonObj();
		int id = req.getInt("id");
		String password = req.getString("password");
		String phonenumber = req.getString("phonenumber");
		String address = req.getString("address");
		
		Member m = new Member(id, password, phonenumber, address);
		JSONObject data = mh.update(m);
		JSONObject jsonObj = new JSONObject();
		
		if(data.getInt("row")>0) {
			jsonObj.put("message", "撌脫�鞈��");
			jsonObj.put("data",data);
			rh.sendJsonRes(jsonObj, response);
		} else {
			jsonObj.put("message", "��鞈�隤�");
			jsonObj.put("data",data);
			rh.sendJsonErr(jsonObj, response);
		}
		
		
		
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestHandler rh = new RequestHandler(request);
		String id = rh.getParameter("id");
		mh.delete(Integer.parseInt(id));
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("message", "撌脣�鞈��");
		rh.sendJsonRes(jsonObj, response);
	}

}
