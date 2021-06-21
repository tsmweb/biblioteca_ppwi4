package br.com.tsmweb.biblioteca.models.service.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.tsmweb.biblioteca.models.model.Role;
import br.com.tsmweb.biblioteca.models.service.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {

	private String secretKey = "secret";
	
	private long validityInMilliseconds = 3600000;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken(String email, List<Role> roles) {
		var claims = Jwts.claims().setSubject(email);
		claims.put("roles", roles);
		
		var now = new Date();
		var validity = new Date(now.getTime() + validityInMilliseconds);
		
		var token = Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(validity)
					.signWith(SignatureAlgorithm.HS256, secretKey)
					.compact();
		
		System.out.println(token);
		
		return token;
	}
	
	public String resolveToken(HttpServletRequest request) {
		var bearerToken = request.getHeader("Authorization");
		
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		
		return null;
	}

	public boolean validateToken(String token) {
		try {
			var claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			
			return true;
		} catch(JwtException | IllegalArgumentException ex) {
			throw new InvalidJwtAuthenticationException("Token inválido ou expirado");
		}
	}

	public Authentication getAuthentication(String token) {
		var usuario = userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(usuario, "", usuario.getAuthorities());
	}

	private String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
}
