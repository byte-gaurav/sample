package com.lowlevel.design.lld.pubsub;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class Broker implements Runnable {
    protected String name;
    protected Map<String, Topic> map;
    protected ExecutorService service; // Not used in this example, but can be used for async processing

    public Broker(String name, ExecutorService service) {
        this.name = name;
        this.map = new HashMap<>();
        this.service = service;
    }

    void createTopic(String topicName) {
        if (this.map.containsKey(topicName)) {
            throw new RuntimeException("Topic already exists");
        }
        this.map.put(topicName, new Topic(topicName));
    }

    void subscribe(String topicName, ISubscriber subscriber) {
        if (!this.map.containsKey(topicName)) {
            throw new RuntimeException("Topic does not exists");
        }
        for (ISubscriber sub : this.map.get(topicName).getSubscriberList()) {
            if (sub.getName().equals(subscriber.getName())) {
                return;
            }
        }
        this.map.get(topicName).getSubscriberList().add(subscriber);
    }

    @Override
    public void run() {
        try {
            while(true) {
                for (Topic topic : this.map.values()) {
                    topic.lock.lock();
                    try {
                        if (topic.messageList.isEmpty()) {
                            topic.notEmpty.await();
                        } else {
                            Message message = topic.messageList.remove(0);
                            topic.notFull.signal(); // Signal BEFORE submitting to executor
                            service.submit(() -> {
                                for (ISubscriber subscriber : topic.getSubscriberList()) {
                                    subscriber.processMessage(message);
                                }
                            });
                        }
                    } finally {
                        topic.lock.unlock();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Broker interrupted: " + e.getMessage());
        }

    }
}

