package com.dhaval.eventhandler;

import com.dhaval.datamodels.Clearable;
import com.lmax.disruptor.EventHandler;

/**
 * Event handler responsible for clearing events in their containers for GC.
 */
public class ClearingEventHandler implements EventHandler<Clearable> {

    @Override
    public void onEvent(Clearable clearable, long l, boolean b) throws Exception {
        clearable.clear();
    }
}
