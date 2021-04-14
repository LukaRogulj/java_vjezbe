package com.example.MqttSubscriber.user.model;

public class MQTTMessage {

    private String value;

    public MQTTMessage(String val) {
        this.value = val;
    }

    public String getValue() {
        return value;
    }

}
