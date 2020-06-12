package main.mrs.isa.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.mrs.model.Korisnik;

@Component
public class TokenUtils {
     
	@Value("klinika")
	private String APP_NAME;

	@Value("tajna")
	public String SECRET;

	@Value("300000")
	private int EXPIRES_IN;

	@Value("Authorization")
	private String AUTH_HEADER;

	private static final String AUDIENCE_UNKNOWN = "unknown";
	private static final String AUDIENCE_WEB = "web";
	private static final String AUDIENCE_MOBILE = "mobile";
	private static final String AUDIENCE_TABLET = "tablet";

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	public String generateToken(String username) {
		return Jwts.builder().setIssuer(APP_NAME).setSubject(username).setAudience(generateAudience())
				.setIssuedAt(new Date()).setExpiration(generateExpirationDate()).signWith(SIGNATURE_ALGORITHM, SECRET)
				.compact();
	}

	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);

		// JWT se prosledjuje kroz header Authorization u formatu: Bearer token
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	
	private String generateAudience() {
		return AUDIENCE_WEB;
	}

	private Date generateExpirationDate() {
		return new Date(new Date().getTime() + EXPIRES_IN);
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			claims.setIssuedAt(new Date());
			refreshedToken = Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public boolean canTokenBeRefreshed(String token, Date date) {
		final Date created = this.getIssuedAtDateFromToken(token);
		return (!(this.isCreatedBeforeLastPasswordReset(created, date))
				&& (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		//Korisnik korisnik = (Korisnik) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);

		return (username != null && username.equals(userDetails.getUsername()));
	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			audience = claims.getAudience();
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		String audience = this.getAudienceFromToken(token);
		return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public int getExpiredIn() {
		return EXPIRES_IN;
	}
}
