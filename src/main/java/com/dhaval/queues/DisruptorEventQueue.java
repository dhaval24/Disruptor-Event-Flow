package com.dhaval.queues;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public class DisruptorEventQueue<T> implements EventQueue {

    private int RINGBUFFER_SIZE = 1024;

    private Disruptor<T> disruptor;

    private RingBuffer<T> ringBuffer;

    private EventFactory<T> eventFactory;

    private ThreadFactory threadFactory;

    private ProducerType producerType;

    private WaitStrategy waitStrategy;

    private Enqueuer enqueuer;

    private abstract class Enqueuer {
        abstract void enqueue(Object o);
    }

    public DisruptorEventQueue(EventTranslatorVararg<T> TRANSLATOR, EventFactory<T> eventFactory,
                               @Nullable ThreadFactory threadFactory,
                               @Nullable ProducerType producerType,
                               @Nullable WaitStrategy waitStrategy) {
        this.eventFactory = eventFactory;
        this.threadFactory = threadFactory != null ? threadFactory : DaemonThreadFactory.INSTANCE;
        this.producerType = producerType != null ? producerType : ProducerType.MULTI;
        this.waitStrategy = waitStrategy != null ? waitStrategy : new SleepingWaitStrategy();
        this.disruptor = new Disruptor<>(eventFactory, RINGBUFFER_SIZE, this.threadFactory, this.producerType, this.waitStrategy);
        this.ringBuffer = disruptor.getRingBuffer();
        this.enqueuer = new Enqueuer() {
            @Override
            void enqueue(Object o) {
                ringBuffer.publishEvent(TRANSLATOR, o);
            }
        };
    }

    @Override
    public void enqueue(Object event) {
        enqueuer.enqueue(event);
    }

    @Override
    public void shutdown() {
        enqueuer = new Enqueuer() {
            final AtomicBoolean shutted = new AtomicBoolean(false);
            @Override
            void enqueue(Object o) {
                if(!shutted.compareAndSet(false, true)) {
                    System.out.println("Attempted to enqueue after disrupter shutted down");
                }
            }
        };

        disruptor.shutdown();
    }

    /**
     * This method must be called only after the dependency graph has been created.
     */
    public void start() {
        disruptor.start();
    }

    public RingBuffer<T> getRingBuffer() {
        return ringBuffer;
    }

    public Disruptor<T> getDisruptor() {
        return disruptor;
    }
}
