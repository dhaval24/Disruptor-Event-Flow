package com.dhaval.datamodels;

/**
 * Container to hold string type event for disruptor queue
 */
public class StringEvent implements Clearable {

    /**
     * Associated String with this StringEvent
     */
    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public void clear() {
        s = null;
    }
}
