package com.dhaval.eventhandler;

import com.dhaval.datamodels.StringEvent;
import com.lmax.disruptor.EventHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class StringBatcherAndPrinterHandler implements EventHandler<StringEvent> {

    private ByteBuffer buffer;

    // TODO: Make this configurable
    private final int MAX_BUFFER_SIZE = 32768;

    // TODO: Make this configurable
    private final String destinationFileName = "output.txt";
    private final FileChannel fileChannel;
    private int counter = 0;
    private boolean isPrinted = false;

    public StringBatcherAndPrinterHandler() throws IOException {
        buffer = ByteBuffer.allocate(MAX_BUFFER_SIZE);
        fileChannel = new FileOutputStream(destinationFileName, false).getChannel();
    }

    @Override
    public void onEvent(StringEvent stringEvent, long l, boolean b) throws Exception {
        counter++;
        if ((counter >= 400000) && (counter % 400000 == 0))
            System.out.println("The " + counter+ " event processed at " + System.currentTimeMillis());
        if (stringEvent.getS().getBytes().length >= buffer.remaining()-10000) {
            long start = System.currentTimeMillis();
            sendBuffer();
            long end = System.currentTimeMillis();
            if (!isPrinted) {
                System.out.println(String.format("Time to send buffer of 1MB to disk %d in millis ", (end-start)));
                isPrinted = true;
            }

        }
        if (b) {
            sendBuffer();
        }
        buffer.put(stringEvent.getS().getBytes());
        buffer.put("\n".getBytes());
    }

    /**
     * Flushes the underlying byte buffer to disk
     */
    private void sendBuffer() {

        try {
            buffer.put("---------------------\n".getBytes());
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to be called after disruptor is shutdown to flush all the remaining data in buffer.
     */
    public void shutdown() {

        if (fileChannel != null) {
            try {
                sendBuffer();
                fileChannel.close();
            } catch (IOException e) {
                System.err.println("Error while closing FileChannel");
                e.printStackTrace();
            }
        }

    }
}
