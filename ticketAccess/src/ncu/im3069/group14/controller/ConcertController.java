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
@WebServlet("/ConcertController")
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
