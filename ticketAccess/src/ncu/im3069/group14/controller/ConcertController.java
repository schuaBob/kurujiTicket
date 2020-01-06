package ncu.im3069.group14.controller;

import java.io.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ncu.im3069.group14.app.*;



import org.json.*;
import ncu.im3069.group14.tools.RequestHandler;

 



/**
 * Servlet implementation class ConcertController
 */
@WebServlet("/api/concert.do")
@MultipartConfig(fileSizeThreshold=1024*1024*10, maxFileSize=1024*1024*50, maxRequestSize=1024*1024*100,location="ticketAccess/picture")
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
        /** ��蝬圾�� JsonReader 銋� Request �� */
		if(!"".equals(jsr.getParameter("session"))){
			String session = jsr.getParameter("session");
			result = ch.getConcertByAttr("session",session);     
		}else if(!"".equals(jsr.getParameter("concertid"))) {
			String concertid = jsr.getParameter("concertid");
			result = ch.getConcertByAttr("idconcert",concertid);			
		}else if( !"".equals(jsr.getParameter("getspecifyconcert")) && (jsr.getMemberIDinRequest()!="0")){
			result = ch.getConcertByAttr("supplierid",jsr.getMemberIDinRequest());
		}else {
			result = ch.getConcertByAttr("",""); //����
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
		String main = request.getParameter("obj");
		JSONObject temp = new JSONObject(main);
		Part seatPicPart = request.getPart("seatpicture");
		Part posterPart = request.getPart("picture");
		System.out.println("seatPicPart: "+seatPicPart.getSubmittedFileName()+", Size: "+seatPicPart.getSize());
		System.out.println("posterPart: "+posterPart.getSubmittedFileName()+", Size: "+posterPart.getSize());
		String seatPicName = seatPicPart.getSubmittedFileName();
		String posterName = posterPart.getSubmittedFileName();
		
		System.out.println(request.getServletContext().getRealPath("/WebContent/picture"));

		seatPicPart.write(seatPicName);
		posterPart.write(posterName);
		temp.put("picture",File.separator+"picture"+File.separator+posterName);
		temp.put("seatpicture", File.separator+"picture"+File.separator+seatPicName);
		/** ��sonReader憿撠equest銋SON�撘��圾��蒂���� */
		
		RequestHandler jsr = new RequestHandler(request);
        Concert c = new Concert(temp);
        
        JSONObject data = ch.createConcert(c);
    	JSONObject resp = new JSONObject();
    	resp.put("status", "200");
        resp.put("message", "���憓����");
        resp.put("row-effect", data);
        
        /** ��sonReader�隞嗅����垢嚗誑JSONObject�撘�� */
        jsr.sendJsonRes(resp, response);
	}
}
