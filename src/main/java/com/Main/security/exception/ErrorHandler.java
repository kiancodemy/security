package com.Main.security.exception;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


import java.io.IOException;
@NoArgsConstructor
public class ErrorHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json,charset=utf-8");
        response.setHeader("KianErrorr", "failed");
        String message=authException!=null&&authException.getMessage()!=null?authException.getMessage():"unauthorized";

        String json = """
        {
          "timestamp": "%s",
          "status": 401,
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
