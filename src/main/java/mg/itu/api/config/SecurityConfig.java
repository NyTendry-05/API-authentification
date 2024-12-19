package mg.itu.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(request -> new CorsConfiguration()
                .applyPermitDefaultValues())) 
            .csrf(csrf -> csrf.disable())
            // .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
            //     .authenticationEntryPoint(unauthorizedHandler))
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui/index.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                // .requestMatchers(HttpMethod.POST, "/utilisateur/login").permitAll()
                // .requestMatchers(HttpMethod.POST, "/utilisateur/verifierCodeValidation").permitAll()
                .requestMatchers("/utilisateur/**").permitAll() 
                .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // .formLogin(formLogin -> formLogin.loginPage("/swagger-ui/index.html"))
            .httpBasic(httpBasic -> httpBasic.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
