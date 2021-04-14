package com.example.MqttSubscriber.user.service;

public interface MessageListener {

    public void messageReceived(String message);

}
