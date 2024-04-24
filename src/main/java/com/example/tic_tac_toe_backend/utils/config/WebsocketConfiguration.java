package com.example.tic_tac_toe_backend.utils.config;

import com.example.tic_tac_toe_backend.utils.filter.CustomHandshakeHandler;
import com.example.tic_tac_toe_backend.utils.filter.HttpHandshakeInterceptor;
import com.example.tic_tac_toe_backend.utils.filter.JwtChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final JwtDecoder jwtDecoder;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new HttpHandshakeInterceptor())
                .setHandshakeHandler(new CustomHandshakeHandler(jwtDecoder))
                .withSockJS(); // endpoint for client to connect to the server
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // prefix for server to send messages to the client
        registry.setApplicationDestinationPrefixes("/app"); // prefix for client to send messages to the server
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtChannelInterceptor());
    }

}
