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
 * Servlet implementation class TicketController
 */
@WebServlet("/ticket")
public class TicketController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private TicketHelper th = TicketHelper.getHelper();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh = new RequestHandler(request);
		JSONObject jso = rh.toJsonObj();
		int orderid = jso.getInt("orderid");
		JSONObject data = th.getTicket(orderid);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("message", "Query success.");
		jsonObj.put("data", data);
		rh.sendJsonRes(jsonObj, response);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("finish doGet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh = new RequestHandler(request);
		JSONObject jso = rh.toJsonObj();
		int idticket = jso.getInt("idticket");
		JSONObject result = th.useTicket(idticket);
		JSONObject resp = new JSONObject();
		
		resp.put("status","200");
		resp.put("message", "票券使用成功！");
        resp.put("result", result);
        rh.sendJsonRes(resp, response);
        
		System.out.println("finish doPost");
	}

}
