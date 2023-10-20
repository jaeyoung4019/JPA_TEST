package toy.project.local_specialty.local_famous_goods.utils.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import toy.project.local_specialty.local_famous_goods.domain.auth.Authority;
import toy.project.local_specialty.local_famous_goods.dto.authority.MemberDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${spring.jwt.secret}")
    private String SPRING_JWT_SECRET;

    public MemberDetails getUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
            return null;
        }
        return (MemberDetails ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String generateToken(long id, String type) {
        return Jwts.builder()
                .claim("id", id)
                .claim("TYPE", type)
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(getKey())
                .compact();
    }

    private Date getExpiration() {
        return Date.from(LocalDateTime.now().with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(SPRING_JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String token) {
        try {
            //  토큰 만료
            final Date expiration = getClaims(token).getExpiration();
            return !expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getMemberId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    public String getType(String token) {
        Claims claims = getClaims(token);
        return claims.get("TYPE", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
    }
}
