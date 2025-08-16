package com.lowlevel.design.lld.pubsub;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Topic {
    protected String name;
    protected List<Message> messageList;
    protected List<ISubscriber> subscriberList;
    protected Lock lock = new ReentrantLock();
    protected Condition notEmpty = lock.newCondition();
    protected Condition notFull = lock.newCondition();

    public Topic(String name) {
        this.name = name;
        this.messageList = new ArrayList<>();
        this.subscriberList = new ArrayList<>();
    }

    public List<ISubscriber> getSubscriberList() {
        return this.subscriberList;
    }
}