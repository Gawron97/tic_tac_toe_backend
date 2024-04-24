package com.example.tic_tac_toe_backend.utils.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.lang.Thread.sleep;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebsocketConnectionFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("WebsocketConnectionFilter is filtering the request.");
        String path = request.getRequestURI();
        log.info("Request path: " + path);
        if (path.startsWith("/ws")) {
            String token = "eyJraWQiOiI1VVdUdXIyZ01CaW8wbTM4MHBEVUozb1JcLzBLS3ZWcUg5V3JtWG1RaEJabz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NDk4MzQ2OC1hMGIxLTcwZDYtY2ZkYS05NDgxZGQwNmJlY2MiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV92MW8xNFVKSmQiLCJjbGllbnRfaWQiOiIyNTBjajVxZmdjamRnZ3NrZzEwbjdsOWlwNCIsIm9yaWdpbl9qdGkiOiIyZTZmZTlhOC01MTEwLTRjNzYtODg4MC1mY2MwMDRhMTU2ZjciLCJldmVudF9pZCI6Ijk5N2IyM2Y5LWFjMzAtNGMwOC1iYWYxLWI3MDM5OWZlNTE4ZiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3MTM4NzIxNDUsImV4cCI6MTcxMzk2MzUxMiwiaWF0IjoxNzEzOTU5OTEyLCJqdGkiOiJjMmNiNTc0My1iNGNjLTQxMGUtOTgyNi0zNzcxYTU1OWIwOTAiLCJ1c2VybmFtZSI6Imdhd3JvbiJ9.FPSoQL6h546V2yDONC94srBrh_5mUSGCrbT6mftCG5Dtl77NplUpG6m7dQvmj8l7Xmc9L6sNAlGKrG8dd0boAub48W1mHC39R7n981lcGbw9WRDXcCkU4UO2v30u9JndomylPbIqB3TEgtb9ErZ4EAXKVaysJRcc9p6eoHCqoZTYpV3g9V-ej9OuznZkVF6URiy9OYjj2mMBWGvo2ovO9P6SN9VhBPwJjCDJxWH7QAigCoHFjjhUrIRfIidex_bwdZaErsEqaUbAfHkugfK6JX3jc5EVa5p1f9l_1aRf6brhE8vg7HVTi3lzTrbumdfw3OdkRmrpzIwfN1IMNAu7og";

            try {
                Jwt jwt = jwtDecoder.decode(token);
                BearerTokenAuthenticationToken auth = new BearerTokenAuthenticationToken(token);
//                new JwtAuthenticationProvider(jwtDecoder).authenticate(auth); // This will throw an exception if the token is invalid (e.g. expired
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("Successfully authenticated user with token");
            } catch (Exception e) {
                log.error("Failed to authenticate user with token", e);
            }
        }

        filterChain.doFilter(request, response);
    }


}
