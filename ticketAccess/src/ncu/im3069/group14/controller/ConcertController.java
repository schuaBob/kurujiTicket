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
		
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		RequestHandler jsr = new RequestHandler(request);
        JSONObject jso = jsr.toJsonObj();
        
        //測試一個json，格式為{concertName:kuruji}
        String concertName = jso.getString("concertName");
        System.out.println(concertName);
        Concert c = new Concert(concertName);
        
        if(concertName.isEmpty()) {
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.sendJsonRes(jso, response);
        }else {
        	//暫時
        	JSONObject data = ch.createConcert(c);
        	JSONObject resp = new JSONObject();
        	resp.put("status", "200");
            resp.put("message", "成功新增演唱會");
            resp.put("row-effect", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.sendJsonRes(resp, response);
        }
	}

}
