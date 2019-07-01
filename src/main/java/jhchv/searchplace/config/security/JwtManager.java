package jhchv.searchplace.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtManager {

    private final Algorithm algorithm;

    private final String issuer;

    public JwtManager() {
        this.algorithm = Algorithm.HMAC256("secret");
        this.issuer = "jhchv";
    }

    public String create(String username) throws JWTCreationException {
        return JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(30L).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("username", username)
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) throws JWTVerificationException {
        return this.jwtVerifier().verify(token);
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return JWT.require(this.algorithm)
                .withIssuer(this.issuer)
                .build();
    }

}
