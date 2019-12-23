package ncu.im3069.group14.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
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
		String authorization = httpRequest.getHeader("Authorization");
		String token = (authorization==null||authorization.isEmpty()) ? null: authorization.split(" ")[1];
		
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
			httpResponse.setHeader("Authorization", "Bearer " + jwt);
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}