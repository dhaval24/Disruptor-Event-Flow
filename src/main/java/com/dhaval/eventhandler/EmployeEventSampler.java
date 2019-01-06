package com.dhaval.eventhandler;


import com.dhaval.datamodels.EmployeEvent;
import com.lmax.disruptor.EventHandler;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Event sampler responsible for sampling Employee event
 */
public class EmployeEventSampler implements EventHandler<EmployeEvent> {

    @Override
    public void onEvent(EmployeEvent employeEvent, long l, boolean b) throws Exception {
        int random = ThreadLocalRandom.current().nextInt(1000);
        if (random % 2 == 0) {
            employeEvent.getEmployee().setSampled(true);
        }
    }
}
