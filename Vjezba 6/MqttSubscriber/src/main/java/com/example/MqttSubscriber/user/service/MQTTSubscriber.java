package com.example.MqttSubscriber.user.service;

import com.example.MqttSubscriber.user.model.MQTTBrokerConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MQTTSubscriber implements MqttCallback {

    private String connectionString;

    private String topic;

    private Queue<String> queue = new LinkedList<>();

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MQTTSubscriber.class);

    private MqttClient mqttClient;

    private MessageListener listener;

    public MQTTSubscriber() {

    }

    public void connect(MQTTBrokerConfig config) throws MqttException {

        this.connectionString = config.getConnectionString();
        this.mqttClient = new MqttClient(this.connectionString, "client1");
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        LOGGER.info("Connecting to broker: {}", this.connectionString);
        this.mqttClient.connect(connOpts);
        LOGGER.info("Connected to {}", this.connectionString);
        this.mqttClient.setCallback(this);
        this.mqttClient.subscribe(config.getTopic(), 0);
        LOGGER.info("Subscribed to {}", config.getTopic());
    }

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void connectionLost(Throwable cause) {
        LOGGER.error("Lost connection", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        synchronized (this.queue) {
            LOGGER.info("Received message: {}", message.toString());
            if (this.queue.size() >= 100) {
                this.queue.remove();
            }
            this.queue.add(message.toString());
            if (this.listener != null) {
                this.listener.messageReceived(message.toString());
            }
        }
    }

    public List<String> messages() {
        return new ArrayList<String>(this.queue);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOGGER.debug("delivery completed");
    }

    public String getConnectionString() {
        return this.connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void disconnect() {
        if (this.mqttClient != null) {
            try {
                this.mqttClient.disconnect();
                this.mqttClient.close();
            } catch (MqttException e) {
                LOGGER.error("Error when closing connection", e);
            }
        }
    }

}