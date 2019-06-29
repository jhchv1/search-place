package jhchv.searchplace.config;

import jhchv.searchplace.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<User> auditorAware() {
        return () -> {
            User user = null;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof User) {
                    user = (User) authentication.getPrincipal();
                }
            }

            return Optional.ofNullable(user);
        };
    }

}
