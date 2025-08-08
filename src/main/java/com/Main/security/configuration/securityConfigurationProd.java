package com.Main.security.configuration;
import com.Main.security.exception.AccesDenidHandler;
import com.Main.security.exception.ErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
@Profile("prod")
@Configuration
public class securityConfigurationProd {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(c->c.invalidSessionUrl("/invalidate").maximumSessions(1).maxSessionsPreventsLogin(true)).csrf(AbstractHttpConfigurer::disable).redirectToHttps(withDefaults()).authorizeHttpRequests((requests) -> requests.requestMatchers("/by").authenticated().requestMatchers("/register").permitAll().anyRequest().permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(c->c.authenticationEntryPoint(new ErrorHandler()));
        http.exceptionHandling(c->c.accessDeniedHandler(new AccesDenidHandler()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}
