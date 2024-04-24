//package com.example.tic_tac_toe_backend.utils.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class CustomAnonymousFilter extends AnonymousAuthenticationFilter {
//    public CustomAnonymousFilter() {
//        super("key");
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.info("CustomAnonymousFilter is filtering the request.");
//
//        if(SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            Authentication anonAuth = new AnonymousAuthenticationToken("key", "anonymousUser",
//                    AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
//            SecurityContextHolder.getContext().setAuthentication(anonAuth);
//            log.info("User is anonymous and has the authorities " + anonAuth.getAuthorities().toString());
//
//        } else {
//            log.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " is successfully authenticated and "
//                    + "has the authorities " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
//        }
//
//        chain.doFilter(request, response);
//
//    }
//
//}
