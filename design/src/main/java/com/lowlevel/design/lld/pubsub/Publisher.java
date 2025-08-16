package com.lowlevel.design.lld.pubsub;

public class Publisher {
    Broker queue;

    public Publisher(Broker queue) {
        this.queue = queue;
    }

    public void publish(String msg, String topicName) {
        try {
            publishCore(msg, topicName);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: " + e.getMessage());
        }
    }

    private void publishCore(String msg, String topicName) throws InterruptedException {
        if (!this.queue.map.containsKey(topicName)) {
            throw new RuntimeException("Topic does not exist");
        }

        Topic topic = this.queue.map.get(topicName);
        topic.lock.lock();
        try {
            Message message = new Message(msg);
            if (topic.messageList.size() >= 5) {
                topic.notFull.await();
            }
            System.out.println("Publishing: " + message);
            topic.messageList.add(message);
            topic.notEmpty.signal();
        } finally {
            topic.lock.unlock();
        }
    }
}