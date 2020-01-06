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

	//瑼ＸToken���iddleware
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		
		String token = null;
		Cookie[] cookies = httpRequest.getCookies();
		//STEP1 敺ookie銝剖��oken
		System.out.println("In filter");
		try {
			for(Cookie c:cookies) {
				if(c.getName().equals("Token")) {
					System.out.println(c.getValue());
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
		
			//STEP2 ���OKEN
			System.out.println("Path:" + path + ", Method: " + httpRequest.getMethod());
			if(token == null||token.isEmpty()) {
				System.out.println("Token is null or empty");
				//STEP2.1 憒�閮餃����靘停瘝�oken嚗�隞亙停銝���(��)
				if((path.equals("/Auth/member")&&httpRequest.getMethod().equals("POST"))||(path.equals("/Auth/concert.do")&&httpRequest.getMethod().equals("GET"))) {
					chain.doFilter(request, response);//�脣雿輻�撓���雯��嚗X 頛詨auth/order > ��隞亥�蝙�� /order
				} else {
					httpResponse.sendRedirect("/login");//瘝�oken嚗�token�蝛箇��誨銵其����嚗��ogin��
					return;
				}
			} else {
				//STEP2.2 憒��oken��
				Claims clmBody = null;
				Cookie jwtCookie = null;
				try {
					//STEP3 token閫�蝣�
					clmBody = Token.decode(token);
				} catch (SignatureException sigE) {
					//STEP3.1 憒���(閫�蝣澆�����)嚗停���ookie鋆⊿��oken����
					System.out.println("The signature is invalid.");
					jwtCookie = new Cookie("Token",null);
					httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);//�����oken
					httpResponse.sendRedirect("/login");//撠�ogin��
					return;
				} catch (ExpiredJwtException expE) {
					//STEP3.2 憒���停�������
					System.out.println("The token is out of date.");
					jwtCookie = new Cookie("Token",null);
					httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);//�����oken
					httpResponse.sendRedirect("/login");//撠�ogin��
					return;
				}
				//STEP4 憒�瘝���停�������ookie嚗靘�摮�����
				String id = clmBody.getSubject();
				System.out.println(id);
				String jwt = Token.createToken(id);
				jwtCookie = new Cookie("Token",jwt);
				httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);
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
