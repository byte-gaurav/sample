package com.lowlevel.design.lld.pubsub;

public class Message {
    private final String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return "{" +
                "message='" + message + '\'' +
                '}';
    }
}