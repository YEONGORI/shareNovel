package novel.server.writer.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private static final int EXPIRATION_MILLISECONDS = 1000 * 60 * 30;
    @Value("${jwt.secret}")
    private String secretKey;
    private byte[] key;

    @PostConstruct
    public void init() {
        this.key = Base64.getDecoder().decode(secretKey);
    }

    public TokenInfo createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date currentTime = new Date();
        Date expirationTime = new Date(currentTime.getTime() + EXPIRATION_MILLISECONDS);

        // Access Token
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuedAt(currentTime)
                .setExpiration(expirationTime)
                .signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS256)
                .compact();

        return new TokenInfo("Bearer", accessToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String auth = claims.get("auth").toString();
        if (auth == null) {
            throw new RuntimeException("잘못된 토큰입니다.");
        }
        List<GrantedAuthority> authorities = Arrays.stream(auth.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
//            if (e instanceof SecurityException) { // Invalid JWT Token
//
//            }
//            if (e instanceof MalformedJwtException) { // Invalid JWT Token
//
//            }
//            if (e instanceof ExpiredJwtException) { // Expired JWT Token
//
//            }
//            if (e instanceof UnsupportedJwtException) { // Unsupported JWT Token
//
//            }
//            if (e instanceof IllegalArgumentException) { // JWT claims string is empty
//
//            }
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token)
                .getBody();
    }
}
