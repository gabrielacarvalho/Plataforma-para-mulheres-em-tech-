package br.com.shecode.config;

import br.com.shecode.domain.auth.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Unauthorized\"}");
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/trails/**").permitAll()
                        .requestMatchers("/forum/posts").permitAll()
                        .requestMatchers("/forum/posts/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth2/authorize")
                        )
                        .successHandler((request, response, authentication) -> {
                            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
                            String name = oidcUser.getFullName() != null ? oidcUser.getFullName() : "Usuária";
                            String photo = oidcUser.getPicture() != null ? oidcUser.getPicture() : "";
                            String url = "https://shecodebr-gabriela-carvalho.vercel.app?login=success&name=" +
                                    java.net.URLEncoder.encode(name, "UTF-8") + "&photo=" +
                                    java.net.URLEncoder.encode(photo, "UTF-8");
                            response.sendRedirect(url);
                        })
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "https://plataforma-para-mulheres-em-tech.vercel.app",
                "https://shecodebr-gabriela-carvalho.vercel.app",
                "https://shecodebr.vercel.app",
                "http://localhost:8080",
                "http://localhost:3000"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        return new UrlBasedCorsConfigurationSource() {{
            registerCorsConfiguration("/**", config);
        }};
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}