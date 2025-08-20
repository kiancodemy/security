package com.Main.security.configuration.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LogoutSuccess implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("User logged out successfully: " +
                (authentication != null ? authentication.getName() : "anonymous"));

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String json = "{\n" +
                "  \"status\": \"success\",\n" +
                "  \"message\": \"Logged out successfully\"\n" +
                "}";
        response.getWriter().write(json);
    }
}
