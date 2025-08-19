package com.Main.security.configuration.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class BasicAuthenticationEntryPointException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json,charset=UTF-8");

        String json = String.format(
                "{ \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\" }",
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                authException.getMessage(),
                request.getRequestURI()
        );

        response.getWriter().write(json);

    }
}
