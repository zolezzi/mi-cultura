package unq.edu.li.pdes.micultura.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("ERROR: " + accessDeniedException.getMessage());
    	logger.error("Access denied: {}", accessDeniedException.getMessage());
        // Puedes personalizar la respuesta HTTP o realizar otras acciones según tu caso
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
    }
}