import com.dhaval.datamodels.Clearable;
import com.dhaval.queues.DisruptorEventQueue;
import org.junit.Assert;
import org.junit.Test;

/**
 * DisruptorTest class
 *
 * Inspired by / Credits: io.opencensus.impl.internal.DisruptorEventQueueTest
 */
public class DisruptorTest {

    // Simple class to use that keeps an incrementing counter. Will fail with an assertion if
    // increment is used from multiple threads, or if the stored value is different from that expected
    // by the caller.
    private static class Counter {
        private int count;
        private volatile long id; // stores thread ID used in first increment operation.

        public Counter() {
            count = 0;
            id = -1;
        }

        // Increments counter by 1. Will fail in assertion if multiple different threads are used
        // (the EventQueue backend should be single-threaded).
        public void increment() {
            long tid = Thread.currentThread().getId();
            if (id == -1) {
                Assert.assertEquals(0, count);
                id = tid;
            } else {
                Assert.assertEquals(tid, id);
            }
            count++;
        }

        // Check the current value of the counter. Assert if it is not the expected value.
        public void check(int value) {
            Assert.assertEquals(value, count);
        }
    }

    // EventQueueEntry for incrementing a Counter.
    private static class IncrementEvent implements Clearable {
        private Counter counter;

        public Counter getCounter() {
            return counter;
        }

        public void setCounter(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void clear() {
            counter = null;
        }
    }

    @Test
    public void incrementOnce() {
        Counter counter = new Counter();
        IncrementEvent ie = new IncrementEvent();
        ie.setCounter(counter);
        DisruptorEventQueue<IncrementEvent> disruptorEventQueue = new DisruptorEventQueue<>(
                (IncrementEvent incrementEvent, long l, Object... objects) -> {
                    incrementEvent.setCounter(((IncrementEvent) objects[0]).getCounter());
                },
                IncrementEvent::new,null, null, null);
        // Sleep briefly, to allow background operations to complete.
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        disruptorEventQueue.getDisruptor().handleEventsWith((IncrementEvent incrementEvent, long l, boolean b) -> {
            incrementEvent.getCounter().increment();
        }).then((Clearable clearble, long l, boolean b) -> clearble.clear());
        disruptorEventQueue.start();
        disruptorEventQueue.enqueue(ie);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupted();
        }
        counter.check(1);
    }

    @Test
    public void incrementTenK() {
        final int tenK = 10000;
        Counter counter = new Counter();
        IncrementEvent ie = new IncrementEvent();
        ie.setCounter(counter);
        DisruptorEventQueue<IncrementEvent> disruptorEventQueue = new DisruptorEventQueue<>(
                (IncrementEvent incrementEvent, long l, Object... objects) -> {
                    incrementEvent.setCounter(((IncrementEvent) objects[0]).getCounter());
                },
                IncrementEvent::new,null, null, null);
        // Sleep briefly, to allow background operations to complete.
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        disruptorEventQueue.getDisruptor().handleEventsWith((IncrementEvent incrementEvent, long l, boolean b) -> {
            incrementEvent.getCounter().increment();
        }).then((Clearable clearble, long l, boolean b) -> clearble.clear());
        disruptorEventQueue.start();
        for (int i = 0; i < tenK; i++) {
            disruptorEventQueue.enqueue(ie);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupted();
        }
        counter.check(tenK);
    }
}
