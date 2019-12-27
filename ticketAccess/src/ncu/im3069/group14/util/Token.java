package ncu.im3069.group14.util;

import io.jsonwebtoken.*;
import java.time.temporal.ChronoUnit;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


public class Token {
	
	private static final String Issuer = "kurujiTicket";
	private static final String Audience = "member";
	private static final String Secret = "kuruji14"; 
	
	public static String createToken(String id) throws UnsupportedEncodingException {
		
		Instant now = Instant.now();
		String jws = Jwts.builder()
				.setHeaderParam("alg", "HS512")
				.setHeaderParam("typ", "jwt")
				.setSubject(id)
				.setIssuer(Token.Issuer)
				.setAudience(Token.Audience)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(60,ChronoUnit.MINUTES)))
				.signWith(SignatureAlgorithm.HS512,Token.Secret.getBytes("UTF-8"))
				.compact();
		return jws;
	}
	
	
	public static Claims decode(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
		Claims temp = Jwts.parser().setSigningKey(Token.Secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
		return temp;
	}
	
	public static HttpServletResponse addTokentoCookie(Cookie jwtCookie,HttpServletResponse response) {
		jwtCookie.setMaxAge(60*60);
		jwtCookie.setHttpOnly(true);
		response.addCookie(jwtCookie);
		return response;
	}
	
	
	
	
}
