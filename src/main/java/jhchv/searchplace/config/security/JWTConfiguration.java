package jhchv.searchplace.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Jihun Cha
 */
@Component
public class JWTConfiguration {

    private final Algorithm algorithm;

    private final String issuer;

    public JWTConfiguration() {
        this.algorithm = Algorithm.HMAC256("secret");
        this.issuer = "jhchv";
    }

    @Bean
    public JWTCreator jwtCreator() {
        return new JWTCreator();
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return JWT.require(this.algorithm)
                .withIssuer(this.issuer)
                .build();
    }

    public class JWTCreator {

        public String create(String username) throws JWTCreationException {
            return JWT.create()
                    .withIssuer(JWTConfiguration.this.issuer)
                    .withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(30L).atZone(ZoneId.systemDefault()).toInstant()))
                    .withClaim("username", username)
                    .sign(JWTConfiguration.this.algorithm);
        }

    }

}
