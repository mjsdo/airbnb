package codesquad.airbnb.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    public boolean validateExpirationOfToken(String token) {
        try {
            return !Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(System.getenv("SECRET_KEY")))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getPayload(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(System.getenv("SECRET_KEY")))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }
}
