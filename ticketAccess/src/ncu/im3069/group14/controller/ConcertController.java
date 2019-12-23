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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/** �z�LJsonReader���O�NRequest��JSON�榡��ƸѪR�è��^ */
		RequestHandler jsr = new RequestHandler(request);
        JSONObject jso = jsr.toJsonObj();
        
        //���դ@��json�A�榡��{concertName:kuruji}
        String concertName = jso.getString("concertName");
        System.out.println(concertName);
        Concert c = new Concert(concertName);
        
        if(concertName.isEmpty()) {
            /** �z�LJsonReader����^�Ǩ�e�ݡ]�H�r��覡�^ */
            jsr.sendJsonRes(jso, response);
        }else {
        	//�Ȯ�
        	JSONObject data = ch.createConcert(c);
        	JSONObject resp = new JSONObject();
        	resp.put("status", "200");
            resp.put("message", "���\�s�W�t�۷|");
            resp.put("row-effect", data);
            
            /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
            jsr.sendJsonRes(resp, response);
        }
	}

}
