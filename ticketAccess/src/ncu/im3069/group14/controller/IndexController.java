package ncu.im3069.group14.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
//import ncu.im3069.group14.tools.RequestHandler;
import ncu.im3069.group14.util.Token;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie[] cookies = request.getCookies();
		String token = null;
		try {
			for (Cookie c:cookies) {
				System.out.println("Name:"+c.getName());
				if(c.getName().equals("Token")) {
					token = c.getValue();
					System.out.println("Incoming token: "+token);
					break;
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
		}
		
		if(token==null||token.isEmpty()) {
			response.sendRedirect("index.html");
		} else {
			try {
				Claims clmBody = Token.decode(token);
			} catch (SignatureException sigE) {
				System.out.println("The signature is invalid.");
				Cookie emptyCookie = new Cookie("Token",null);
				response.addCookie(emptyCookie);
				response.sendRedirect("index.html");
				return;
			} catch (ExpiredJwtException expE) {
				System.out.println("The token is out of date.");
				Cookie emptyCookie = new Cookie("Token",null);
				response.addCookie(emptyCookie);
				response.sendRedirect("index.html");
				return;
			}
//			response.setHeader("Authorization", "Bearer " + token);
			System.out.println("Is logined.");
			response.sendRedirect("index-signin.html");
			return;
		}
	}

}
