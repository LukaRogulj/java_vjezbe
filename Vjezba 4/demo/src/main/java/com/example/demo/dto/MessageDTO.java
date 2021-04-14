package com.example.demo.dto;

public class MessageDTO {

    private String topic;

    private String message;

    // private String protocol;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDTO [message=" + message + "]";
    }

}