package jhchv.searchplace.config.security;

import com.auth0.jwt.JWTVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Jihun Cha
 */
@EnableWebSecurity
public class WebSecurityConfiguration {

    public static HttpSecurity init(HttpSecurity http) throws Exception {
        return http
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .csrf().disable();
    }

    @Configuration
    @Order(1)
    public static class TokenWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            WebSecurityConfiguration.init(http)
                    .antMatcher("/token")

                    .authorizeRequests()
                    .anyRequest().authenticated()

                    .and()
                    .httpBasic();
        }

    }

    @Configuration
    @Order(2)
    @RequiredArgsConstructor
    public static class ApiWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final JWTVerifier jwtVerifier;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            WebSecurityConfiguration.init(http)
                    .antMatcher("/api/**")

                    .authorizeRequests()
                    .antMatchers("/api/keyword-ranks", "/api/search").permitAll()
                    .anyRequest().authenticated()

                    .and()
                    .addFilterAfter(new JWTAuthenticationFilter(jwtVerifier), BasicAuthenticationFilter.class);
        }

    }

    @Configuration
    public static class DefaultWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/", "/webjars/**", "/js/**", "/css/**", "/favicon.ico");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            WebSecurityConfiguration.init(http)
                    .authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()

                    .and()
                    .headers()
                    .frameOptions().sameOrigin();
        }

    }

}
