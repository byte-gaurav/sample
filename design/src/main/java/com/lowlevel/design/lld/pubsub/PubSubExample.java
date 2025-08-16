package com.lowlevel.design.lld.pubsub;

import com.lowlevel.design.lld.pubsub.subscriber.NewsSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PubSubExample {
    public static void main(String[] args) throws InterruptedException {
        List<ISubscriber> newSubscribers = new ArrayList<>();
        newSubscribers.add(new NewsSubscriber("AajTak"));
        newSubscribers.add(new NewsSubscriber("NDTV"));
        newSubscribers.add(new NewsSubscriber("ZeeNews"));
        newSubscribers.add(new NewsSubscriber("RepublicBharat"));

        ExecutorService executorService = Executors.newFixedThreadPool(newSubscribers.size());
        Broker broker = new Broker("MainBroker", executorService);
        broker.createTopic("News");

        Publisher newsPublisher = new Publisher(broker);
        for (ISubscriber subscriber : newSubscribers) {
            broker.subscribe("News", subscriber);
        }

        Thread brokerThread = new Thread(broker);
        brokerThread.start();

        for(int i = 0; i < 20; i++) {
            newsPublisher.publish("Message " + (i + 1), "News");
        }

        System.out.println("All messages published. Waiting for subscribers to process till 5 secs...");
        Thread.sleep(20000);
        System.out.println("Stopping broker thread...");
        brokerThread.interrupt();
        executorService.shutdownNow();
    }
}