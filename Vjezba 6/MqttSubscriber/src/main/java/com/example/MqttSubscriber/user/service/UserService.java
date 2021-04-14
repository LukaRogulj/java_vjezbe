package com.example.MqttSubscriber.user.service;

import com.example.MqttSubscriber.user.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

}
