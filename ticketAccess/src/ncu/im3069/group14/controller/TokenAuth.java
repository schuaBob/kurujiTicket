package ncu.im3069.group14.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.*;
import ncu.im3069.group14.util.Token;

/**
 * Servlet Filter implementation class TokenAuth
 */
@WebFilter("/Auth/*")
public class TokenAuth implements Filter {


	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	//ÀË¬dToken¥Îªºmiddleware
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		
		String token = null;
		Cookie[] cookies = httpRequest.getCookies();
		
		try {
			for(Cookie c:cookies) {
				if(c.getName().equals("Token")) {
					token = c.getValue();
					System.out.println("Incoming token: "+token);
					break;
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
		}
			
		
		
		
//		if(path.equals("/Auth/member")&&httpRequest.getMethod().equals("POST")) {
//			chain.doFilter(request, response);
//		} else {
			if(token == null||token.isEmpty()) {
				if(path.equals("/Auth/member")&&httpRequest.getMethod().equals("POST")) {
					chain.doFilter(request, response);
				} else {
					httpResponse.sendRedirect("/login");
				}
			} else {
				Claims clmBody = null;
				Cookie jwtCookie = null;
				try {
					clmBody = Token.decode(token);
				} catch (SignatureException sigE) {
					System.out.println("The signature is invalid.");
					jwtCookie = new Cookie("Token",null);
					httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);
					httpResponse.sendRedirect("/login");
				} catch (ExpiredJwtException expE) {
					System.out.println("The token is out of date.");
					jwtCookie = new Cookie("Token",null);
					httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);
					httpResponse.sendRedirect("/login");
				}
				String id = clmBody.getSubject();
				String jwt = Token.createToken(id);
				jwtCookie.setValue(jwt);
				httpResponse.addCookie(jwtCookie);
				
				chain.doFilter(request, response);
			}
//		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
