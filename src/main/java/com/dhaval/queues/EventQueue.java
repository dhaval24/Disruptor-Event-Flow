package com.dhaval.queues;

public interface EventQueue {

    void enqueue(Object event);

    void shutdown();

}
