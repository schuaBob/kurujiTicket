package ncu.im3069.group14.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ncu.im3069.group14.app.*;
import org.json.*;
import ncu.im3069.group14.tools.RequestHandler;

/**
 * Servlet implementation class OrderController2
 */
@WebServlet("/order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	private OrderHelper oh = OrderHelper.getHelper();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh = new RequestHandler(request);
		JSONObject data = oh.getOrder();
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("message", "Query success.");
		jsonObj.put("data", data);
		rh.sendJsonRes(jsonObj, response);
		
		System.out.println("before response");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("after response");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh = new RequestHandler(request);
		JSONObject jso = rh.toJsonObj();
		
		//int memberid = Integer.parseInt(jso.getString("memberid"));
		int memberid = jso.getInt("memberid");
		String payment = jso.getString("payment");
		//int ticketamount = Integer.parseInt(jso.getString("ticketamount"));
		int ticketamount = jso.getInt("ticketamount");
		
		Order o = new Order( memberid, payment, ticketamount);
		
		JSONObject result = oh.create(o);
		
		JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "訂單新增成功！");
        resp.put("result", result);
        rh.sendJsonRes(resp, response);
        
		System.out.println("finish");
		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
