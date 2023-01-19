package com.ars.alpha;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageConfig implements WebSocketMessageBrokerConfigurer
{
    /**
     * Creates possible areas to connect to from front-end application
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/message").setAllowedOrigins("https://localhost:8080").withSockJS();
        registry.addEndpoint("/myTest").setAllowedOrigins("https://localhost:8080").withSockJS();
        registry.addEndpoint("/panic").setAllowedOrigins("https://localhost:8080").withSockJS();
    }

    /**
     * Creates distinction between sessions in connetion to the Spring Boot server
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }
}
