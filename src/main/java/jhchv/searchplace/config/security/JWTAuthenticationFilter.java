package jhchv.searchplace.config.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jihun Cha
 */
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTVerifier jwtVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.toLowerCase().startsWith("bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);
            DecodedJWT jwt = this.jwtVerifier.verify(token);

            Authentication auth = new UsernamePasswordAuthenticationToken(jwt.getClaim("username").asString(), null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        catch (JWTVerificationException ex) {
            int status = HttpStatus.UNAUTHORIZED.value();
            response.setStatus(status);
            response.getWriter().write(String.format("{\"status\":%d,\"code\":\"%s\",\"message\":\"%s\"}",
                    status, ex instanceof TokenExpiredException ? "TOKEN_EXPIRED" : "UNDEFINED", ex.getMessage()));
            return;
        }

        chain.doFilter(request, response);
    }

}
