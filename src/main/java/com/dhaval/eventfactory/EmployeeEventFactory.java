package com.dhaval.eventfactory;

import com.dhaval.datamodels.EmployeEvent;
import com.lmax.disruptor.EventFactory;

/**
 * EventFactory used to produce EmployeEvents to be published in RingBuffer.
 */
public enum EmployeeEventFactory implements EventFactory<EmployeEvent> {

    INSTANCE;

    public EmployeEvent newInstance() {
        return new EmployeEvent();
    }
}
