package com.Main.security.configuration.provider;
import com.Main.security.configuration.AuthenticService.MainUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
@Profile("prod")
@Component
@RequiredArgsConstructor
public class AuthenticationProviderProd implements AuthenticationProvider {
    private final MainUserDetailService mainUserDetailService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=authentication.getCredentials().toString();
        UserDetails userDetails = mainUserDetailService.loadUserByUsername(username);
        boolean check=passwordEncoder.matches(password, userDetails.getPassword());
        if(check){
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }
        else {
            throw new BadCredentialsException("invalid password");

        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
