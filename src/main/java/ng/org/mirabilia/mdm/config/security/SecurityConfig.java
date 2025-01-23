package ng.org.mirabilia.mdm.config.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import ng.org.mirabilia.mdm.services.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public AuthenticationContext authenticationContext() {
        return new AuthenticationContext();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/")
                        .permitAll()
                        // Allow access to static resources
                        .requestMatchers("/image/**", "/css/**", "/js/**", "/frontend/**", "/VAADIN/**", "/static/**", "/webjars/**", "/favicon.ico")
                        .permitAll()
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/", true) // Redirect to home after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Redirect to login page if trying to access a page without authentication
                            response.sendRedirect("/login");
                        })
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity (not recommended for production)

        return http.build();
    }


}
