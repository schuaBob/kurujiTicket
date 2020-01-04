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

	//檢查Token用的middleware
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		
		String token = null;
		Cookie[] cookies = httpRequest.getCookies();
		
		//STEP1 從cookie中取得token
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
		
			//STEP2 處理TOKEN
			if(token == null||token.isEmpty()) {
				//STEP2.1 如果是註冊，因為本來就沒有token，所以就不處理(判斷)
				if(path.equals("/Auth/member")&&httpRequest.getMethod().equals("POST")) {
					chain.doFilter(request, response);//進入使用者輸入的網址，EX 輸入auth/order > 這行可以讓使用者去 /order
				} else {
					httpResponse.sendRedirect("/login");//沒有token，或是token是空的，代表他沒有登入，導回login畫面
				}
			} else {
				//STEP2.2 如果有token值
				Claims clmBody = null;
				Cookie jwtCookie = null;
				try {
					//STEP3 token解碼
					clmBody = Token.decode(token);
				} catch (SignatureException sigE) {
					//STEP3.1 如果不安全(解碼出現問題)，就會把cookie裡面的token刪掉
					System.out.println("The signature is invalid.");
					jwtCookie = new Cookie("Token",null);
					httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);//產生新的token
					httpResponse.sendRedirect("/login");//導回login畫面
          return;
				} catch (ExpiredJwtException expE) {
					//STEP3.2 如果過期就做這些動作
					System.out.println("The token is out of date.");
					jwtCookie = new Cookie("Token",null);
					httpResponse = Token.addTokentoCookie(jwtCookie, httpResponse);//產生新的token
					httpResponse.sendRedirect("/login");//導回login畫面
          return;
				}
				//STEP4 如果都沒問題，就產生一個新的cookie，用來刷新存在時間。
				String id = clmBody.getSubject();
				System.out.println(id);
				String jwt = Token.createToken(id);
				jwtCookie = new Cookie("Token",jwt);
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
