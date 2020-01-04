package ncu.im3069.group14.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ncu.im3069.group14.tools.*;
import ncu.im3069.group14.util.Token;
import ncu.im3069.group14.app.*;
import java.sql.Date;



import org.json.JSONObject;


/**
 * Servlet implementation class IndexController
 */
@WebServlet("/Auth/member")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MemberHelper mh = MemberHelper.getHelper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In member get");
		RequestHandler rh = new RequestHandler(request);
		String id = rh.getMemberIDinRequest();
		System.out.println(id);
		JSONObject memberData = mh.readByID(id);
		
		JSONObject resObj = new JSONObject();
		if(!id.equals("0")) {
			resObj.put("message", "Query success.");
			resObj.put("memberData", memberData);
			rh.sendJsonRes(resObj, response);
		} else {
			resObj.put("message", "Query error.");
			
			rh.sendJsonErr(resObj, response);
		}
		

	}

	/**
	 * 	會員註冊
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//先將註冊的request傳到requesthandler，接著轉成JSONObject 的型態
		RequestHandler rh = new RequestHandler(request);
		JSONObject req = rh.toJsonObj();
		
		//取得dob欄位的資料
		Date dob = Date.valueOf(req.getString("dob"));
		//取得name欄位的資料
		String name = req.getString("name");
		//取得password欄位的資料
		String password = req.getString("password");
		//取得email欄位的資料
		String email = req.getString("email");
		//取得idnumber
		String idn= req.getString("idnumber");
		//phonenumber
		String phonenumber = req.getString("phonenumber");
		//address
		String address = req.getString("address");
		//透過以上的資料建立一個Member物件
		Member m = new Member(name,password,email,dob,idn,phonenumber,address);
		
		JSONObject resObj = new JSONObject();
		
		//如果欄位資料有被註冊過
		if(mh.isExist(m)) {
			
			//回傳被註冊過的訊息
			resObj.put("message", "資料已被註冊");
			rh.sendJsonErr(resObj, response);
		}else {
			
			//如果沒被註冊過，就寫入資料庫
			JSONObject res = mh.create(m);
			
			//如果回傳的row大於0的話代表存入成功
			if(res.getInt("row")>0) {
				
				//回傳註冊成功的訊息
				resObj.put("message", "註冊成功");
				resObj.put("redirect", "signin.html");
				rh.sendJsonRes(resObj, response);
				
				//如果存取錯誤
			} else {
				
				//回傳存取失敗的訊息
				resObj.put("message", "註冊失敗");
				resObj.put("res",res);
				rh.sendJsonErr(resObj, response);
			}
			
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestHandler rh = new RequestHandler(request);
		JSONObject req = rh.toJsonObj();
		int id = Integer.parseInt(rh.getMemberIDinRequest());
		String password = req.getString("password");
		String phonenumber = req.getString("phonenumber");
		String address = req.getString("address");
		
		Member m = new Member(id, password, phonenumber, address);
		JSONObject memRes = mh.update(m);
		JSONObject resObj = new JSONObject();
		
		if(memRes.getInt("row")>0) {
			Cookie jwtCookie = new Cookie("Token",null);
			Token.addTokentoCookie(jwtCookie, response);
			resObj.put("message", "修改成功");
			resObj.put("redirect", "signin.html");
			rh.sendJsonRes(resObj, response);
		} else {
			resObj.put("message", "修改失敗");
			rh.sendJsonErr(resObj, response);
		}
		
		
		
	}
	
//	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		RequestHandler rh = new RequestHandler(request);
//		String id = rh.getParameter("id");
//		mh.delete(Integer.parseInt(id));
//		JSONObject jsonObj = new JSONObject();
//		
//		jsonObj.put("message", "撌脣�鞈��");
//		rh.sendJsonRes(jsonObj, response);
//	}

}
