package com.dhaval.eventtranslators;

import com.dhaval.datamodels.StringEvent;
import com.lmax.disruptor.EventTranslatorVararg;


/**
 * Populates the StringEvent object in Ring buffer with Actual Object Published
 */
public enum ObjectToStringTranslator implements EventTranslatorVararg<StringEvent> {

    INSTANCE;

    @Override
    public void translateTo(StringEvent stringEvent, long l, Object... objects) {
        stringEvent.setS((String) objects[0]);
    }
}
