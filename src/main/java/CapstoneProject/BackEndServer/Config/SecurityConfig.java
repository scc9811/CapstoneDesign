package CapstoneProject.BackEndServer.Config;


import CapstoneProject.BackEndServer.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String secretKey;
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
//                .cors(CorsConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        httpRequest -> httpRequest
                                .requestMatchers("/user/*").permitAll()
                                .requestMatchers("/ping/*").permitAll()
                                .requestMatchers("/socketConnection").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(secretKey, userService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
