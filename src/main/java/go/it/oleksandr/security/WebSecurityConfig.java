package go.it.oleksandr.security;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final DataSource dataSource;

    private static final String BY_USERNAME_QUERY = "SELECT username, password, enabled FROM users WHERE username = ?";
    private static final String BY_USERNAME_ROLE_QUERY = "SELECT username, role FROM users WHERE username =?";

    @Autowired
    public void configAuthentication(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery(BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(BY_USERNAME_ROLE_QUERY);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/note/home", true))
                .build();
    }
}