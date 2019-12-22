//package ncu.im3069.group14.util;
//
//import io.jsonwebtoken.*;
//import javax.servlet.http.*;
//import java.time.temporal.ChronoUnit;
//import java.io.UnsupportedEncodingException;
//import java.time.Instant;
//import java.util.Date;
//
//public class Token {
//	
//	private static final String Issuer = "kurujiTicket";
//	private static final String Audience = "Customer";
//	private static final String Secret = "kuruji14"; 
//	
//	public void create(HttpServletRequest request) throws UnsupportedEncodingException {
//		
//		Instant now = Instant.now();
//		
//		String jwt = Jwts.builder()
//				.setIssuer(Token.Issuer)
//				.setSubject(request.getParameter("id"))
//				.setAudience(Token.Audience)
//				.setIssuedAt(Date.from(now))
//				.setExpiration(Date.from(now.plus(1,ChronoUnit.HOURS)))
//				.signWith(SignatureAlgorithm.HS512,Token.Secret.getBytes("UTF-8"))
//				.compact();
//	}
//	
//	public void decode(HttpServletRequest request) {
//		try {
//			Jwts.parser()
//			.setSigningKey(Token.Secret.getBytes("UTF-8"))
//			.par
//		}
//	}
//}
