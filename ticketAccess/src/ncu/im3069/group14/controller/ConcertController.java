package ncu.im3069.group14.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ncu.im3069.group14.app.*;
import java.sql.Date;
import java.util.ArrayList;

import org.json.*;
import ncu.im3069.group14.tools.RequestHandler;


/**
 * Servlet implementation class ConcertController
 */
@WebServlet("/api/concert.do")
public class ConcertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ConcertHelper ch = ConcertHelper.getHelper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConcertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestHandler jsr = new RequestHandler(request);
		JSONArray result = new JSONArray();
        /** ���X�g�ѪR�� JsonReader �� Request �Ѽ� */
		if(!"".equals(jsr.getParameter("session"))){
			String session = jsr.getParameter("session");
			result = ch.getConcertByAttr("session",session);     
		}else if(!"".equals(jsr.getParameter("concertid"))) {
			String concertid = jsr.getParameter("concertid");
			result = ch.getConcertByAttr("idconcert",concertid);			
		}
		
		if(result.isEmpty()) {
        	JSONObject resp = new JSONObject();
        	resp.put("status", "200");
            resp.put("data", "{}");
            resp.put("isEmpty", "true");
        	jsr.sendJsonRes(resp, response);
        }else {
        	JSONObject resp = new JSONObject();
        	resp.put("status", "200");
            resp.put("data", result.toString());
            resp.put("isEmpty", "false");
            jsr.sendJsonRes(resp, response);
        }     
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/** �z�LJsonReader���O�NRequest��JSON�榡��ƸѪR�è��^ */
		RequestHandler jsr = new RequestHandler(request);
        JSONObject jso = jsr.toJsonObj();
        Concert c = new Concert(jso);
        
        JSONObject data = ch.createConcert(c);
    	JSONObject resp = new JSONObject();
    	resp.put("status", "200");
        resp.put("message", "���\�s�W�t�۷|");
        resp.put("row-effect", data);
        
        /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
        jsr.sendJsonRes(resp, response);
	}
}
