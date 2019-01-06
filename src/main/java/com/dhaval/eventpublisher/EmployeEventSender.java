package com.dhaval.eventpublisher;

import com.dhaval.datamodels.EmployeEvent;
import com.dhaval.datamodels.StringEvent;
import com.dhaval.queues.DisruptorEventQueue;
import com.lmax.disruptor.EventHandler;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * The consumer that converts the Employee to Json String and acts as producer
 * for second Ring buffer.
 */
public class EmployeEventSender implements EventHandler<EmployeEvent> {

    private DisruptorEventQueue<StringEvent> disruptorEventQueue;
    private ObjectMapper objectMapper;
    private int count = 0;

    public EmployeEventSender(DisruptorEventQueue<StringEvent> disruptorEventQueue) {
        this.disruptorEventQueue = disruptorEventQueue;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void onEvent(EmployeEvent employeEvent, long l, boolean b) throws Exception {
        if (!employeEvent.getEmployee().isSampled()) {
            String asJson = objectMapper.writeValueAsString(employeEvent.getEmployee());
            disruptorEventQueue.enqueue(asJson);
        }
        count++;
        if (count == 4000000) {
            System.out.println("Last event processed at EmployeEventSender at " + System.currentTimeMillis());
        }
    }
}
