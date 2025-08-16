package com.lowlevel.design.lld.pubsub.subscriber;

import com.lowlevel.design.lld.pubsub.ISubscriber;
import com.lowlevel.design.lld.pubsub.Message;

import java.util.concurrent.ThreadLocalRandom;

public class NewsSubscriber implements ISubscriber {
    private final String name;
    private int waitTime;

    public NewsSubscriber(String name) {
        this.name = name;
        this.waitTime = ThreadLocalRandom.current().nextInt(500, 6000); // Random wait time between 500ms and 2000ms
    }

    @Override
    public void processMessage(Message message) {
        System.out.println(name + " received: " + message.getMessage());
        try {
            Thread.sleep(waitTime); // Simulate processing time
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return name;
    }
}