package com.topicossoftware.marino.upb.edu.api.api.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class JWTFilter implements Filter {

    @Autowired
    private JWTVerifier verifier;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.debug("Ingresando a Filtro");
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String token = httpRequest.getHeader("token");

        if (token != null) {
            if (isValidToken(token)) {
                // Set authentication context if valid token
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                // Continue with the request
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Hubo un error al acceder el recurso");
            }
        } else {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No se tienen las autorizaciones necesarias");
        }
    }

    private boolean isValidToken(String token) {
        log.info("Verificando token");
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.info("Token no valido: "+e.getMessage());
            return false;
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic, if any
    }
}