package jhchv.searchplace.config.security;

import com.auth0.jwt.JWTVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Jihun Cha
 */
@EnableWebSecurity
public class WebSecurityConfiguration {

    public static void init() {

    }

    @Configuration
    @Order(1)
    public static class TokenWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/token")

                    .authorizeRequests()
                    .anyRequest().authenticated()

                    .and()
                    .httpBasic()

                    .and()
                    .csrf().disable()

                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    }

    @Configuration
    @Order(2)
    @RequiredArgsConstructor
    public static class ApiWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final JWTVerifier jwtVerifier;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")

                    .authorizeRequests()
                    .antMatchers("/api/keyword-ranks", "/api/search").permitAll()
                    .anyRequest().authenticated()

                    .and()
                    .addFilterAfter(new JWTAuthenticationFilter(jwtVerifier), BasicAuthenticationFilter.class)
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    }

    @Configuration
    public static class DefaultWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/h2-console/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/", "/webjars/**", "/js/**", "/css/**", "/favicon.ico").permitAll()
                    .anyRequest().authenticated();
        }

    }

}
