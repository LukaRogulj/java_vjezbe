package com.example.MqttSubscriber.user.repository;

import com.example.MqttSubscriber.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
