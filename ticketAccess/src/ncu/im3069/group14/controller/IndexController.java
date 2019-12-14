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
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberHelper mh = MemberHelper.getHelper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexController() {
        super();
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
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Date dob = Date.valueOf("1998-10-13");
		Member m = new Member("華崧淇","a29252097","schua1013@gmail.com",dob,"A123456789","0912345678","NCU");
		JSONObject res = mh.create(m);
		
		response.getWriter().append(res.toString());
	}


}
