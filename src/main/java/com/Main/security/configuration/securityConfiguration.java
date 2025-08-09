package com.Main.security.configuration;

import com.Main.security.configuration.handler.FailureHandler;
import com.Main.security.configuration.handler.SuccessHandler;
import com.Main.security.exception.AccesDenidHandler;
import com.Main.security.exception.ErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
@RequiredArgsConstructor
@Profile("!prod")
@Configuration
public class securityConfiguration {
    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http .cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(AbstractHttpConfigurer::disable).sessionManagement(c -> c.invalidSessionUrl("/invalidate").maximumSessions(3).maxSessionsPreventsLogin(true)).authorizeHttpRequests((requests) -> requests.requestMatchers("/by").authenticated().requestMatchers("/register", "/invalidate").permitAll().anyRequest().authenticated());
        http.formLogin(loginForm -> loginForm.loginProcessingUrl("/api/login").usernameParameter("username").passwordParameter("password").successHandler(successHandler).failureHandler(failureHandler))
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(new AccesDenidHandler())
                        .authenticationEntryPoint(new ErrorHandler())
                ).httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // your React frontend URL
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true); // important if you use cookies/session

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}
