package codesquad.airbnb.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Random;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final long accessTokenValidityInMilliseconds = 60; // access token 유효시간 : 1분
    private static final long refreshTokenValidityInMilliseconds = 60 * 30; // refresh token 유효시간 : 30분

    public String createAccessToken(String payload) {
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String valueForRefreshToken = new String(array, StandardCharsets.UTF_8);
        return createToken(valueForRefreshToken, refreshTokenValidityInMilliseconds);
    }

    private String createToken(String payload, long expireTime) {
        if (expireTime <= 0) {
            throw new RuntimeException("토큰의 만료시간은 0 보다 커야 합니다.");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(System.getenv("SECRET_KEY"));
        Key signatureKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
            .setSubject(payload)
            .signWith(signatureKey, signatureAlgorithm)
            .setExpiration(new Date(System.currentTimeMillis() + expireTime))
            .compact();
    }
}
