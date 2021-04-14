package com.example.MqttSubscriber.user.repository;

import com.example.MqttSubscriber.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
