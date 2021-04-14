package com.example.MqttSubscriber;

import com.example.MqttSubscriber.user.web.MQTTHandler;
import com.example.MqttSubscriber.user.service.MQTTSubscriber;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@EnableAutoConfiguration
public class AppConfig implements WebSocketConfigurer {

    @Bean
    public MQTTSubscriber subscriber() {
        return new MQTTSubscriber();
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler(), "/accelerometer").withSockJS();
    }

    @Bean
    public WebSocketHandler handler() {
        return new MQTTHandler();
    }

}