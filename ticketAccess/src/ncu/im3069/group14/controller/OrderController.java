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
	private TicketHelper th = TicketHelper.getHelper();
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
		JSONObject jso = rh.toJsonObj();
		
		int memberid = jso.getInt("memberid");
		JSONObject data = oh.getAllOrder(memberid);
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("message", "Query success.");
		jsonObj.put("data", data);
		rh.sendJsonRes(jsonObj, response);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("finish doGet");
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh = new RequestHandler(request);
		JSONObject jso = rh.toJsonObj();
		JSONObject resp = new JSONObject();
		JSONObject temp = null;
		//STEP1 取得request的資料
		int memberid = jso.getInt("memberid");
		String payment = jso.getString("payment");
		int ticketamount = jso.getInt("ticketamount");
		int concertid = jso.getInt("concertid");
		int totalprice = jso.getInt("totalprice");
		
		//STEP2 創造order物件
		Order d = new Order( memberid, payment, ticketamount, concertid, totalprice);
		JSONObject result = oh.create(d);
		
		//STEP2.5 嚙瞑嚙稻嚙瞌嚙稻嚙踝蕭嚙穀嚙諍立，嚙緘嚙瘦嚙踝蕭嚙穀嚙諍立，嚙褒備建立莎蕭嚙踝蕭
		if ( result.getString("result") == "create order success" || result.getString("result") == "update order success") {
			System.out.println("create order success");
			//STEP3 嚙篁嚙緻嚙踝蕭嚙踝蕭捊嚙�
			temp = result.getJSONObject("order");
			
			int orderid = temp.getInt("idorder");
			String seatarea = jso.getString("seatarea");
			//seatid嚙緯嚙踝蕭嚙篁嚙踝蕭concerthelper嚙緯 嚙緻嚙諉喉蕭嚙踝蕭嚙緯嚙踝蕭wjc
			int seatid = jso.getInt("seatid");
			//STEP4 嚙誹據訂嚙踝蕭A嚙諍立莎蕭嚙踝蕭 
			Ticket t = new Ticket( concertid, orderid, seatarea, seatid);
			JSONObject ticketresult = th.create(t, ticketamount);
			
			
	        resp.put("status", "200");
	        resp.put("message", "嚙緬嚙踝蕭s嚙磕嚙踝蕭嚙穀嚙瘢");
	        resp.put("order result", result);
	        resp.put("ticket result", ticketresult);
	        rh.sendJsonRes(resp, response);
		}else {
			//STEP3-1 嚙稷嚙褒伐蕭嚙諸蛛蕭嚙瘦
			resp.put("status","200");
			resp.put("message", "嚙緬嚙踝蕭s嚙磕嚙踝蕭嚙踝蕭");
	        resp.put("order result",result);
	        rh.sendJsonErr(resp, response);
			System.out.println("finish doPut");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh =  new RequestHandler(request);
		JSONObject jso = rh.toJsonObj();
		
		int idorder = jso.getInt("idorder");
		JSONObject result = oh.getPaidOrder(idorder);
		JSONObject resp = new JSONObject();
		resp.put("status", "200");
        resp.put("message", "嚙緬嚙踝蕭I嚙誹佗蕭嚙穀嚙瘢");
        resp.put("result", result);
        rh.sendJsonRes(resp, response);
        
		System.out.println("finish doPost");
		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestHandler rh = new RequestHandler(request);
		JSONObject jso = rh.toJsonObj();
		
		int idorder = jso.getInt("idorder");
		//嚙緯嚙踝蕭嚙磋嚙踝蕭Ticket嚙羯嚙踝蕭R嚙踝蕭order
		JSONObject ticketresult = th.cancelTicket(idorder);
		JSONObject result = oh.cancelOrder(idorder);
		
		
		JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "嚙緬嚙踝蕭R嚙踝蕭嚙踝蕭嚙穀嚙瘢");
        resp.put("order result", result);
        resp.put("ticket result", ticketresult);
        rh.sendJsonRes(resp, response);
        
		System.out.println("finish doDelete");
	}

}
