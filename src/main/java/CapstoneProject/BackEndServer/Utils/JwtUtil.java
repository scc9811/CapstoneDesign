package CapstoneProject.BackEndServer.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {
    public static boolean isExpired(String token, String secretKey) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getExpiration().before(new Date());
        }
        catch (Exception e) {
            return true;
        }
    }
    public static String createJwt(String email, String secretKey, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);


        log.info("token 발급 완료.");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String getUserEmail(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("email", String.class);
    }

}
