package com.Main.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

@NoArgsConstructor
public class AccesDenidHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader("KianError", "failed");
        response.setContentType("application/json,charset=utf-8");
        String message=accessDeniedException!=null&&accessDeniedException.getMessage()!=null?accessDeniedException.getMessage():"unauthorized";

        String json = """
        {
          "timestamp": "%s",
          "status": 403,
          "error": "Unauthorized",
          "message": "%s",
          "path": "%s"
        }
        """.formatted(
                java.time.Instant.now(),
                message,
                request.getRequestURI()
        );

        response.getWriter().write(json);

    }
}
