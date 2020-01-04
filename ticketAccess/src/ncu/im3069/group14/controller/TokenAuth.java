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

	//�ˬdToken�Ϊ�middleware
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		
		String token = null;
		Cookie[] cookies = httpRequest.getCookies();
		
		//STEP1 �qcookie�����otoken
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
		
			//STEP2 �B�zTOKEN
			if(token == null||token.isEmpty()) {
				//STEP2.1 �p�G�O���U�A�]�����ӴN�S��token�A�ҥH�N���B�z(�P�_)
				if(path.equals("/Auth/member")&&httpRequest.getMethod().equals("POST")) {
					chain.doFilter(request, response);//�i�J�ϥΪ̿�J�����}�AEX ��Jauth/order > �o��i�H���ϥΪ̥h /order
				} else {
					httpResponse.sendRedirect("/login");//�S��token�A�άOtoken�O�Ū��A�N��L�S���n�J�A�ɦ^login�e��
				}
			} else {
				//STEP2.2 �p�G��token��
				Claims clmBody = null;
				Cookie jwtCookie = null;
				try {
					//STEP3 token�ѽX
					clmBody = Token.decode(token);
				} catch (SignatureException sigE) {
					//STEP3.1 �p�G���w��(�ѽX�X�{���D)�A�N�|��cookie�̭���token�R��
					System.out.println("The signature is invalid.");
					jwtCookie = new Cookie("Token",null);
					httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);//���ͷs��token
					httpResponse.sendRedirect("/login");//�ɦ^login�e��
				} catch (ExpiredJwtException expE) {
					//STEP3.2 �p�G�L���N���o�ǰʧ@
					System.out.println("The token is out of date.");
					jwtCookie = new Cookie("Token",null);
					httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);//���ͷs��token
					httpResponse.sendRedirect("/login");//�ɦ^login�e��
				}
				//STEP4 �p�G���S���D�A�N���ͤ@�ӷs��cookie�A�ΨӨ�s�s�b�ɶ��C
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
