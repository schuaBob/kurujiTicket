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
@WebServlet("/Auth/order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	private OrderHelper oh = OrderHelper.getHelper();
	private TicketHelper th = TicketHelper.getHelper();
	private ConcertHelper ch = ConcertHelper.getHelper();
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
		
		int memberid = Integer.parseInt(rh.getMemberIDinRequest());
		JSONObject data = oh.getAllOrder(memberid);
		JSONObject jsonObj = new JSONObject();
		
		if ( data.getString("result") == "get member all data success") {
			jsonObj.put("message", "查詢成功");
			jsonObj.put("data", data);
			rh.sendJsonRes(jsonObj, response);
		}else {
			jsonObj.put("message", "查詢失敗，沒有資料");
			jsonObj.put("data", data);
			rh.sendJsonErr(jsonObj, response);
		}
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
		//order要用的資料
		int memberid = jso.getInt("memberid");
		String payment = jso.getString("payment");
		int ticketamount = jso.getInt("ticketamount");
		int concertid = jso.getInt("concertid");
		int totalprice = jso.getInt("totalprice");
		//ticket要用的資料
		String name = jso.getString("name");
		String phonenumber = jso.getString("phonenumber");
		String email = jso.getString("email");
		String seatarea = jso.getString("seatarea");

		//STEP2 建立訂單
		Order o = new Order( memberid, payment, ticketamount, concertid, totalprice);
		JSONObject result = oh.create(o); //會判斷是新建立訂單，還是

		
		//STEP2.5 判斷建立訂單的結果，是否成功建立、成功更新
		if ( result.getString("result") == "create order success") {
			//成功建立新訂單
			System.out.println("create order success");
			
			//STEP3 取得票券資料
			temp = result.getJSONObject("order");
			int orderid = temp.getInt("idorder");
			int seatid = ch.getSeatId(concertid, seatarea, ticketamount);//跟concerthelper取得座位ID
			
			//STEP4 建立新的票券，同時取得建立新票券的結果
			Ticket t = new Ticket( concertid, orderid, seatarea, seatid, email, phonenumber,name );
			JSONObject ticketresult = th.create(t, ticketamount);
			
			//STEP5 把結果放response裡面
	        resp.put("status", "200");
	        resp.put("message", "建立訂單成功");
	        resp.put("order result", result);
	        resp.put("ticket result", ticketresult);
	        rh.sendJsonRes(resp, response);
	        
		}else if (result.getString("result") == "update order success") {
			//成功更新訂單
			System.out.println("update order success");
			
			//STEP3 取得票券資料
			temp = result.getJSONObject("order");
			int orderid = temp.getInt("idorder");
			int seatid = ch.getSeatId(concertid, seatarea, ticketamount);//跟concerthelper取得座位ID
			
			//STEP4 建立新的票券，同時取得建立新票券的結果
			Ticket t = new Ticket( concertid, orderid, seatarea, seatid, email, phonenumber,name );
			JSONObject ticketresult = th.create(t, ticketamount);
			
			//STEP5 把結果放數response裡面
	        resp.put("status", "200");
	        resp.put("message", "更新訂單成功");
	        resp.put("order result", result);
	        resp.put("ticket result", ticketresult);
	        rh.sendJsonRes(resp, response);
			
		}else {
			//失敗的結果
			System.out.println("update/create order failed");
			
			//STEP3 如果失敗，回傳err
			resp.put("status","200");
			resp.put("message", "建立訂單失敗");
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
		if ( result.getString("result") == "order paid success") {
			resp.put("status", "200");
	        resp.put("message", "成功付款");
	        resp.put("result", result);
	        rh.sendJsonRes(resp, response);
		}else {
			resp.put("status", "200");
	        resp.put("message", "付款失敗");
	        resp.put("result", result);
	        rh.sendJsonErr(resp, response);
		}
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

        if ( result.getString("result") == "delete order success") {
            resp.put("status", "200");
            resp.put("message", "成功刪除訂單");
            resp.put("order result", result);
            resp.put("ticket result", ticketresult);
            rh.sendJsonRes(resp, response);
        }else {
            resp.put("status", "200");
            resp.put("message", "刪除訂單失敗，查無訂單資料");
            resp.put("order result", result);
            resp.put("ticket result", ticketresult);
            rh.sendJsonErr(resp, response);
        }
		System.out.println("finish doDelete");
	}

}
