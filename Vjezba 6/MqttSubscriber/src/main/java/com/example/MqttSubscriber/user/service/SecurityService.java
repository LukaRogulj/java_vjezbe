package com.example.MqttSubscriber.user.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
