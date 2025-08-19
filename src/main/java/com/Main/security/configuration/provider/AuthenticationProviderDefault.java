package com.Main.security.configuration.provider;
import com.Main.security.configuration.AuthenticService.MainUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
@Profile("!prod")
@Component
@RequiredArgsConstructor
public class AuthenticationProviderDefault implements AuthenticationProvider {
    private final MainUserDetailService  mainUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=authentication.getCredentials().toString();
        UserDetails userDetails = mainUserDetailService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken( username, password, userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
