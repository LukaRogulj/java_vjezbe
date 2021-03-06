package com.example.MqttSubscriber.user.web;

import com.example.MqttSubscriber.user.service.MQTTSubscriber;
import com.example.MqttSubscriber.user.service.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MQTTHandler extends TextWebSocketHandler implements MessageListener {

    @Autowired
    private MQTTSubscriber sub;

    private WebSocketSession session;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MQTTHandler.class);

    public MQTTHandler() {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Websocket session established");
        this.session = session;
        this.sub.setMessageListener(this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        LOGGER.debug("Recevied message {}", message.getPayload());
    }

    @Override
    public void messageReceived(String message) {
        try {
            LOGGER.info("Send new message to client {}", message);
            if (this.session.isOpen()) {
                this.session.sendMessage(new TextMessage(message));
            } else {
                LOGGER.warn("Session is closed!");
            }
        } catch (Exception e) {
            LOGGER.error("Error when sending message", e);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        LOGGER.info("Websocket session is closed {}", closeStatus);
    }

}