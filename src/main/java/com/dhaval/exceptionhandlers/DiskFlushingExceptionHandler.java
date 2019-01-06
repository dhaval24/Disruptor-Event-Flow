package com.dhaval.exceptionhandlers;

import com.dhaval.datamodels.StringEvent;
import com.lmax.disruptor.ExceptionHandler;

/**
 * A sample exception handler which can be used to handle retry's and failures
 * with Disk Flushing.
 */
public class DiskFlushingExceptionHandler implements ExceptionHandler<StringEvent> {

    @Override
    public void handleEventException(Throwable throwable, long l, StringEvent s) {
        System.out.println("Exception when sending event : " + s);
        throw new RuntimeException(throwable);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        System.out.println("Exception while starting");
        throw new RuntimeException(throwable);
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        System.out.println("Exception while shutting down");
        throw new RuntimeException(throwable);
    }
}
