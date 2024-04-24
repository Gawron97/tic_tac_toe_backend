package com.example.tic_tac_toe_backend.utils.config;

import com.example.tic_tac_toe_backend.utils.filter.AddParameterToHeaderFilter;
import com.example.tic_tac_toe_backend.utils.filter.CORSFilter;
import com.example.tic_tac_toe_backend.utils.filter.CustomTokenResolver;
import com.example.tic_tac_toe_backend.utils.filter.WebsocketConnectionFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Slf4j
@Configuration
public class SecurityConfig {

    private final WebsocketConnectionFilter websocketConnectionFilter;
    private final ApplicationContext applicationContext;
    private final AddParameterToHeaderFilter addParameterToHeaderFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        httpSecurity
//                .addFilterBefore(websocketConnectionFilter, AnonymousAuthenticationFilter.class)
//                .addFilterBefore(addParameterToHeaderFilter, BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt();

        return httpSecurity.build();

    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return new CustomTokenResolver();
    }


}