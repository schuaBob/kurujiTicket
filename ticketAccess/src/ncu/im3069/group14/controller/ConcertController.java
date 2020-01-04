package ncu.im3069.group14.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ncu.im3069.group14.app.*;
import java.sql.Date;
import java.util.ArrayList;

import org.json.*;
import ncu.im3069.group14.tools.RequestHandler;


/**
 * Servlet implementation class ConcertController
 */
@WebServlet("/api/concert.do")
@MultipartConfig
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
        /** 取出經解析到 JsonReader 之 Request 參數 */
		if(!"".equals(jsr.getParameter("session"))){
			System.out.println("a");
			String session = jsr.getParameter("session");
			result = ch.getConcertByAttr("session",session);     
		}else if(!"".equals(jsr.getParameter("concertid"))) {
			System.out.println("b");
			String concertid = jsr.getParameter("concertid");
			result = ch.getConcertByAttr("idconcert",concertid);			
		}else if( !"".equals(jsr.getParameter("getspecifyconcert")) && (jsr.getMemberIDinRequest()!="0")){
			result = ch.getConcertByAttr("supplierid",jsr.getMemberIDinRequest());
		}else {
			System.out.println("c");
			result = ch.getConcertByAttr("",""); //回傳全部
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
		
//		Part seatPicFile = request.getPart("seatpicture");
//		String fileName = Paths.get(seatPicFile.getSubmittedFileName()).getFileName().toString();
//		InputStream fileContent = seatPicFile.getInputStream();
//		System.out.println(fileName);
//		String path= getServletContext().getRealPath("/WebContent/picture");
//		System.out.println(path);
//		File file = new File(path, fileName);
//		System.out.println(file.getAbsolutePath());
		
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		RequestHandler jsr = new RequestHandler(request);
        JSONObject jso = jsr.toJsonObj();
        Concert c = new Concert(jso);
        
        JSONObject data = ch.createConcert(c);
    	JSONObject resp = new JSONObject();
    	resp.put("status", "200");
        resp.put("message", "成功新增演唱會");
        resp.put("row-effect", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.sendJsonRes(resp, response);
	}
}
