package ncu.im3069.group14.controller;

import java.io.*;
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
        /** ���X�g�ѪR�� JsonReader �� Request �Ѽ� */
        String session = jsr.getParameter("session");
        System.out.println("session="+session);
        JSONArray result = ch.getConcertBySession(session); 
        System.out.println(result.toString());
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
//		String[] fileNameSplit = fileName.split(".");
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
