package com.lowlevel.design.lld.pubsub;

public interface ISubscriber {
    void processMessage(Message message);
    String getName();
}