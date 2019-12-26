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
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		String authorization = httpRequest.getHeader("Authorization");
//		String token = (authorization==null||authorization.isEmpty()) ? null: authorization.split(" ")[1];
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
//		System.out.println(path);
//		System.out.println(httpRequest.getMethod());
		String token = null;
		Cookie jwtCookie = null;
		Cookie[] cookies = httpRequest.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				if(c.getName().equals("Token")) {
					token = c.getValue();
					jwtCookie = c;
					break;
				}
			}
		} else {
			System.out.println("no cookie");
		}
		if(path.equals("/Auth/member")&&httpRequest.getMethod().equals("POST")) {
			chain.doFilter(request, response);
		} else {
			if(token == null) {
				httpResponse.sendRedirect("/login");
			} else {
				Claims clmBody = null;
				try {
					clmBody = Token.decode(token);
				} catch (SignatureException sigE) {
					System.out.println("The signature is invalid.");
					httpResponse.setHeader("Authorization", null);
					httpResponse.sendRedirect("/login");
				} catch (ExpiredJwtException expE) {
					System.out.println("The token is out of date.");
					httpResponse.setHeader("Authorization", null);
					httpResponse.sendRedirect("/login");
				}
				String id = clmBody.getSubject();
				String jwt = Token.createToken(id);
				jwtCookie.setValue(jwt);
				httpResponse.addCookie(jwtCookie);
				httpResponse.setHeader("Authorization", "Bearer " + jwt);
				chain.doFilter(request, response);
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
