package com.Main.security.event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Authentication {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event){
        log.info("Authentication Success",event.getAuthentication().getName());

    }
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event){
        log.info("Authentication failed",event.getAuthentication().getName(),event.getException().getMessage());

    }
}
