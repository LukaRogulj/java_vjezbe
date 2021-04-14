package com.example.demo.controller;

import com.example.demo.dto.MessageDTO;
import com.example.demo.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MqttController {

    @Autowired
    private MqttService mqttService;

    @PutMapping("/start")
    public String startMqtt() {
        return null;
    }

    @PostMapping("/publish")
    public String publish(@RequestBody MessageDTO messageDTO) {
        mqttService.publish(messageDTO.getTopic(), messageDTO.getMessage());
        return "Success";
    }

}