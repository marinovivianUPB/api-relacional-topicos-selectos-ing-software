package com.topicossoftware.marino.upb.edu.api.api.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "JWTFilter", urlPatterns = {"/usuarios/*", "/compradores/*"})
public class JWTFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Token");

        if (token != null) {
            if (isValidToken(token)) {
                // Set authentication context if valid token
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                // Continue with the request
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            }
        } else {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing");
        }
    }

    private boolean isValidToken(String token) {
        // Token validation logic (e.g., check if it's expired, signature match, etc.)
        return token.equals("valid-token"); // Simplified for this example
    }

    @Override
    public void destroy() {
        // Cleanup logic, if any
    }
}