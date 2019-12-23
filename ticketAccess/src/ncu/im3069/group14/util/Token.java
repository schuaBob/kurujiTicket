package ncu.im3069.group14.util;

import io.jsonwebtoken.*;
import java.time.temporal.ChronoUnit;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

public class Token {
	
	private static final String Issuer = "kurujiTicket";
	private static final String Audience = "member";
	private static final String Secret = "kuruji14"; 
	
	public static String createToken(String id) throws UnsupportedEncodingException {
		
		Instant now = Instant.now();
		String jwt = Jwts.builder()
				.setIssuer(Token.Issuer)
				.setSubject(id)
				.setAudience(Token.Audience)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(20,ChronoUnit.MINUTES)))
				.signWith(SignatureAlgorithm.HS512,Token.Secret.getBytes("UTF-8"))
				.compact();
		return jwt;
	}
	
	
	public static Claims decode(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
		Claims temp = Jwts.parser().setSigningKey(Token.Secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
		return temp;
	}
	
	
	
}
