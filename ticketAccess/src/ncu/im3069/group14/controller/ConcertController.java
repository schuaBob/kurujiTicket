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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.*;
import ncu.im3069.group14.tools.RequestHandler;

 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;


/**
 * Servlet implementation class ConcertController
 */
@WebServlet("/api/concert.do")
//@MultipartConfig
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
//		Part main = request.getPart("obj");
//		System.out.println(main);
		
//		upload(request,response);
		
//		String fileName = Paths.get(seatPicFile.getSubmittedFileName()).getFileName().toString();
//		InputStream fileContent = seatPicFile.getInputStream();
//		System.out.println(fileName);
//		String[] fileNameSplit = fileName.split(".");
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
//	  public void upload(HttpServletRequest request, HttpServletResponse response)throws ServletException, java.io.IOException {
//		  
//		  boolean isMultipart;
//		  String filePath = getServletContext().getInitParameter("file-upload");
//		  int maxFileSize = 1024 * 1024;
//		  int maxMemSize = 1024 * 1024;
//		  File file ;
//		  // Check that we have a file upload request
//	      isMultipart = ServletFileUpload.isMultipartContent(request);
//	      response.setContentType("text/html");
//	      java.io.PrintWriter out = response.getWriter( );
//	   
//	      if( !isMultipart ) {
//	         out.println("<html>");
//	         out.println("<head>");
//	         out.println("<title>Servlet upload</title>");  
//	         out.println("</head>");
//	         out.println("<body>");
//	         out.println("<p>No file uploaded</p>"); 
//	         out.println("</body>");
//	         out.println("</html>");
//	         return;
//	      }
//	  
//	      DiskFileItemFactory factory = new DiskFileItemFactory();
//	   
//	      // maximum size that will be stored in memory
//	      factory.setSizeThreshold(maxMemSize);
//	   
//	      // Location to save data that is larger than maxMemSize.
//	      factory.setRepository(new File("c:\\temp"));
//
//	      // Create a new file upload handler
//	      ServletFileUpload upload = new ServletFileUpload(factory);
//	   
//	      // maximum file size to be uploaded.
//	      upload.setSizeMax( maxFileSize );
//
//	      try { 
//	         // Parse the request to get file items.
//	         List fileItems = upload.parseRequest(request);
//		
//	         // Process the uploaded file items
//	         Iterator i = fileItems.iterator();
//
//	         out.println("<html>");
//	         out.println("<head>");
//	         out.println("<title>Servlet upload</title>");  
//	         out.println("</head>");
//	         out.println("<body>");
//	   
//	         while ( i.hasNext () ) {
//	            FileItem fi = (FileItem)i.next();
//	            if ( !fi.isFormField () ) {
//	               // Get the uploaded file parameters
//	               String fieldName = fi.getFieldName();
//	               String fileName = fi.getName();
//	               String contentType = fi.getContentType();
//	               boolean isInMemory = fi.isInMemory();
//	               long sizeInBytes = fi.getSize();
//	            
//	               // Write the file
//	               if( fileName.lastIndexOf("\\") >= 0 ) {
//	                  file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
//	               } else {
//	                  file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
//	               }
//	               fi.write( file ) ;
//	               out.println("Uploaded Filename: " + fileName + "<br>");
//	            }
//	         }
//	         out.println("</body>");
//	         out.println("</html>");
//	         } catch(Exception ex) {
//	            System.out.println(ex);
//	         }
//	      }
}
