package com.Main.security.configuration;
import com.Main.security.configuration.exceptions.AccessDeniedException;
import com.Main.security.configuration.exceptions.BasicAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(s->s.maximumSessions(2).maxSessionsPreventsLogin(true)).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) -> requests.requestMatchers("/users/register","/users/notice").permitAll().requestMatchers("/users/user").authenticated());
        http.formLogin(login->login.usernameParameter("username").passwordParameter("password"));
        http.httpBasic(basic->basic.authenticationEntryPoint(new BasicAuthenticationEntryPointException()));
        http.exceptionHandling(c->c.accessDeniedHandler(new AccessDeniedException()));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
