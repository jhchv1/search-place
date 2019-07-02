package jhchv.searchplace.controller.token;

import jhchv.searchplace.config.security.JWTConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jihun Cha
 */
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final JWTConfiguration.JWTCreator jwtCreator;

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Token doPost(Authentication auth) {
        return new Token(jwtCreator.create(auth.getName()));
    }

}
