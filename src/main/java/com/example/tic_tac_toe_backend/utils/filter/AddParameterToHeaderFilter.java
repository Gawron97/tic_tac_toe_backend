package com.example.tic_tac_toe_backend.utils.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AddParameterToHeaderFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/ws")) {
//            String paramValue = request.getParameter("token");
            String paramValue = "eyJraWQiOiI1VVdUdXIyZ01CaW8wbTM4MHBEVUozb1JcLzBLS3ZWcUg5V3JtWG1RaEJabz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NDk4MzQ2OC1hMGIxLTcwZDYtY2ZkYS05NDgxZGQwNmJlY2MiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV92MW8xNFVKSmQiLCJjbGllbnRfaWQiOiIyNTBjajVxZmdjamRnZ3NrZzEwbjdsOWlwNCIsIm9yaWdpbl9qdGkiOiIyZTZmZTlhOC01MTEwLTRjNzYtODg4MC1mY2MwMDRhMTU2ZjciLCJldmVudF9pZCI6Ijk5N2IyM2Y5LWFjMzAtNGMwOC1iYWYxLWI3MDM5OWZlNTE4ZiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3MTM4NzIxNDUsImV4cCI6MTcxMzk2MzUxMiwiaWF0IjoxNzEzOTU5OTEyLCJqdGkiOiJjMmNiNTc0My1iNGNjLTQxMGUtOTgyNi0zNzcxYTU1OWIwOTAiLCJ1c2VybmFtZSI6Imdhd3JvbiJ9.FPSoQL6h546V2yDONC94srBrh_5mUSGCrbT6mftCG5Dtl77NplUpG6m7dQvmj8l7Xmc9L6sNAlGKrG8dd0boAub48W1mHC39R7n981lcGbw9WRDXcCkU4UO2v30u9JndomylPbIqB3TEgtb9ErZ4EAXKVaysJRcc9p6eoHCqoZTYpV3g9V-ej9OuznZkVF6URiy9OYjj2mMBWGvo2ovO9P6SN9VhBPwJjCDJxWH7QAigCoHFjjhUrIRfIidex_bwdZaErsEqaUbAfHkugfK6JX3jc5EVa5p1f9l_1aRf6brhE8vg7HVTi3lzTrbumdfw3OdkRmrpzIwfN1IMNAu7og";
            if(paramValue != null) {
                request.setAttribute("Authentication", "Bearer " + paramValue);
            }
            log.info("Successfully added parameter to header");
            log.info(String.valueOf(request.getHeaderNames()));
        }


        filterChain.doFilter(request, response);
    }
}

