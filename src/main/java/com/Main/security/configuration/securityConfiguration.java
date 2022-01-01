package com.Main.security.configuration;
import com.Main.security.configuration.handler.LogoutSuccess;
import com.Main.security.configuration.handler.SuccessHandler;
import com.Main.security.exception.AccesDenidHandler;
import com.Main.security.exception.ErrorHandler;
import com.Main.security.filter.CsrfFilterKian;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@RequiredArgsConstructor
@Profile("!prod")
@Configuration
public class securityConfiguration {
    private final SuccessHandler successHandler;
    private final LogoutSuccess logoutSuccess;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler=new CsrfTokenRequestAttributeHandler();
        http.securityContext(c->c.requireExplicitSave(false)).cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("http://localhost:5173"));
                config.setAllowedMethods(List.of("*"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);
                return config;

            }
        })).csrf(cs->cs.ignoringRequestMatchers("/register","/api/login").csrfTokenRequestHandler(csrfTokenRequestAttributeHandler).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())).addFilterAfter(new CsrfFilterKian(),BasicAuthenticationFilter.class).sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)).authorizeHttpRequests((requests) -> requests.requestMatchers("/by","/user").hasRole("USER").requestMatchers("/register", "/invalidate").permitAll().anyRequest().permitAll());
        http.formLogin(loginForm -> loginForm.loginProcessingUrl("/api/login").usernameParameter("username").passwordParameter("password").successHandler(successHandler)).logout(c->c.logoutUrl("/api/logout").invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccess)).
                httpBasic(basic -> basic.authenticationEntryPoint(new ErrorHandler())).exceptionHandling(ex -> ex.accessDeniedHandler(new AccesDenidHandler()));
        return http.build();
    };


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


}
