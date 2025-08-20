package com.Main.security.configuration.handler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email=authentication.getName();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json,charset=UTF-8");

        String json = String.format(
                "{ \"status\": %d, \"email\": \"%s\", \"path\": \"%s\" }",
                HttpStatus.OK.value(),
                email,
                request.getRequestURI()
        );

        response.getWriter().write(json);


    }
}

