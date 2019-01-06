package com.dhaval.eventfactory;

import com.dhaval.datamodels.StringEvent;
import com.lmax.disruptor.EventFactory;

/**
 * Event factory used to publish StringEvents to ring buffer.
 */
public enum StringEventFactory implements EventFactory<StringEvent> {
    INSTANCE;

    @Override
    public StringEvent newInstance() {
        return new StringEvent();
    }
}
