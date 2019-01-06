package com.dhaval;

import com.dhaval.datamodels.AbstractEmployee;
import com.dhaval.datamodels.CustomOfficer;
import com.dhaval.datamodels.EmployeEvent;
import com.dhaval.datamodels.EmployeType;
import com.dhaval.datamodels.SoftwareEngineer;
import com.dhaval.datamodels.StringEvent;
import com.dhaval.eventfactory.EmployeeEventFactory;
import com.dhaval.eventfactory.StringEventFactory;
import com.dhaval.eventhandler.ClearingEventHandler;
import com.dhaval.eventhandler.EmployeAddressAppenderHandler;
import com.dhaval.eventhandler.EmployeEventSampler;
import com.dhaval.eventhandler.StringBatcherAndPrinterHandler;
import com.dhaval.eventpublisher.EmployeEventSender;
import com.dhaval.eventtranslators.EmployeEventTranslator;
import com.dhaval.eventtranslators.ObjectToStringTranslator;
import com.dhaval.exceptionhandlers.DiskFlushingExceptionHandler;
import com.dhaval.queues.DisruptorEventQueue;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class EmployeeEventMain {

    public static void main(String[] args) throws Exception {

        /**
         * Create Employee Event Disruptor
         */
        DisruptorEventQueue<EmployeEvent> disruptorEventQueue = new DisruptorEventQueue<>(EmployeEventTranslator.INSTANCE,
                EmployeeEventFactory.INSTANCE, DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());

        /**
         * Create output disruptor
         */
        DisruptorEventQueue<StringEvent> outputDisruptor = new DisruptorEventQueue<>(ObjectToStringTranslator.INSTANCE,
                StringEventFactory.INSTANCE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        outputDisruptor.getDisruptor().setDefaultExceptionHandler(new DiskFlushingExceptionHandler());

        /**
         * Handler that converts and published Employee to output RingBuffer
         */
        StringBatcherAndPrinterHandler stringBatcherAndPrinterHandler = new StringBatcherAndPrinterHandler();

        /**
         * Set event handlers to output disruptor
         */
        outputDisruptor.getDisruptor().handleEventsWith(stringBatcherAndPrinterHandler).then(new ClearingEventHandler());

        /**
         * Start Output disruptor first
         */
        outputDisruptor.start();

        /**
         * Set event handlers to Input disruptor and start it.
         */
        disruptorEventQueue.getDisruptor().handleEventsWith(new EmployeAddressAppenderHandler()).then(new EmployeEventSampler()).
                then(new EmployeEventSender(outputDisruptor)).
                then(new ClearingEventHandler());

        disruptorEventQueue.start();

        // TODO: This should be configurable
        long maxEventPerThread = 100000;

        int numCores = Runtime.getRuntime().availableProcessors();

        // Simulate producers == numCores
        ExecutorService service = Executors.newFixedThreadPool(4);
        long startTime = System.currentTimeMillis();
        for (int j = 0; j < numCores; j++) {
            service.execute(() -> {
                // Simulate 10 events per producer
                for (long i = 0; i< 100000; ++i) {
                    String name = "ThreadName: " + Thread.currentThread().getName() + " Iter: "+ i;
                    int random = ThreadLocalRandom.current().nextInt(1000);
                    if (random % 2 == 0) {
                        disruptorEventQueue.enqueue(new SoftwareEngineer("software Engineer " + name, "company " + name,
                                new AbstractEmployee.Address(), EmployeType.SOFTWARE_ENGINEER));
                    } else {
                        disruptorEventQueue.enqueue(new CustomOfficer("Custom Officer " + name, "company " + name,
                                new AbstractEmployee.Address(), EmployeType.CIVIL_OFFICER));
                    }
                }
            });
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("ALl items are enqueued at " + endTime1);
        System.out.println(String.format("%d, Events processed by pipeline, in %d milli seconds", (numCores * maxEventPerThread), (endTime1 - startTime)));

        service.shutdown();
        while (!service.isTerminated()) {}

        disruptorEventQueue.shutdown();
        outputDisruptor.shutdown();

        stringBatcherAndPrinterHandler.shutdown();

        long endTime = System.currentTimeMillis();

        System.out.println(String.format("%d, Events processed by pipeline, in %d milli seconds", (numCores * maxEventPerThread), (endTime - startTime)));

    }
}
