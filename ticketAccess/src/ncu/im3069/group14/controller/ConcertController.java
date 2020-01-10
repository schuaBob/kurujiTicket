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
@WebServlet("/Auth/concert.do")
@MultipartConfig(fileSizeThreshold=1024*1024*10, maxFileSize=1024*1024*50, maxRequestSize=1024*1024*100)
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

	//執行前端get指令
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取得前端資料並轉成JSONArray格式
		RequestHandler jsr = new RequestHandler(request);
		JSONArray result = new JSONArray();
        //判斷網址的parameter，並提供相對應的處理
		//session=場次 , concerdid=演唱會id , getspecifyconcert=取得特定supplierid的演唱會內容
		if(!"".equals(jsr.getParameter("session"))){
			String session = jsr.getParameter("session");
			result = ch.getConcertByAttr("session",session);     
		}else if(!"".equals(jsr.getParameter("concertid"))) {
			String concertid = jsr.getParameter("concertid");
			result = ch.getConcertByAttr("idconcert",concertid);			
		}else if( !"".equals(jsr.getParameter("getspecifyconcert")) && (jsr.getMemberIDinRequest()!="0")){
			result = ch.getConcertByAttr("supplierid",jsr.getMemberIDinRequest());
		}else {
			result = ch.getConcertByAttr("",""); //無特定指定參數，回傳全部
		}
		
		//如果result是空的，回傳含空的物件的JSONObject，反之，回傳含結果的JSONObject
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
		
		RequestHandler jsr = new RequestHandler(request);
		
		String main = request.getParameter("obj");
		JSONObject temp = new JSONObject(main);
		Part seatPicPart = request.getPart("seatpicture");
		Part posterPart = request.getPart("picture");
		String seatPicName = seatPicPart.getSubmittedFileName();
		String posterName = posterPart.getSubmittedFileName();		
		String path = request.getServletContext().getRealPath(File.separator+"picture");
		seatPicPart.write(path+File.separator+seatPicName);
		posterPart.write(path+File.separator+posterName);
		temp.put("picture",File.separator+"picture"+File.separator+posterName);
		temp.put("seatpicture", File.separator+"picture"+File.separator+seatPicName);
		
		//新增一個含有須新增資料的物件
        Concert c = new Concert(temp);
        //於資料庫新增演唱會
        JSONObject data = ch.createConcert(c);
        //將結果塞入resp並回傳前端
    	JSONObject resp = new JSONObject();
    	resp.put("status", "200");
        resp.put("message", "新增成功!");
        resp.put("row-effect", data);
        jsr.sendJsonRes(resp, response);
	}
}
