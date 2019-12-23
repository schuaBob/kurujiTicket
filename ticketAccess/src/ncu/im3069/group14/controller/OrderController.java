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
		//STEP1 ���o�q��Ѽ�
		int memberid = jso.getInt("memberid");
		String payment = jso.getString("payment");
		int ticketamount = jso.getInt("ticketamount");
		int concertid = jso.getInt("concertid");
		
		//STEP2 �إ߭q��
		Order o = new Order( memberid, payment, ticketamount, concertid);
		JSONObject result = oh.create(o);
		
		//STEP2.5 �P�_�O�_���\�إߡA�p�G���\�إߡA�ǳƫإ߲���
		if ( result.getString("result") == "create order success" || result.getString("result") == "update order success") {
			System.out.println("create order success");
			//STEP3 �h�o����Ѽ�
			temp = result.getJSONObject("order");
			
			int orderid = temp.getInt("idorder");
			String seatarea = jso.getString("seatarea");
			//seatid�n���h��concerthelper�n �o�ӳ����n��wjc
			int seatid = jso.getInt("seatid");
			//STEP4 �ھڭq��A�إ߲��� 
			Ticket t = new Ticket( concertid, orderid, seatarea, seatid);
			JSONObject ticketresult = th.create(t, ticketamount);
			
			
	        resp.put("status", "200");
	        resp.put("message", "�q��s�W���\�I");
	        resp.put("order result", result);
	        resp.put("ticket result", ticketresult);
	        rh.sendJsonRes(resp, response);
		}else {
			//STEP3-1 �^�ǥ��ѵ��G
			resp.put("status","200");
			resp.put("message", "�q��s�W����");
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
        resp.put("message", "�q��I�ڦ��\�I");
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
		//�n���R��Ticket�~��R��order
		JSONObject ticketresult = th.cancelTicket(idorder);
		JSONObject result = oh.cancelOrder(idorder);
		
		
		JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "�q��R�����\�I");
        resp.put("order result", result);
        resp.put("ticket result", ticketresult);
        rh.sendJsonRes(resp, response);
        
		System.out.println("finish doDelete");
	}

}
