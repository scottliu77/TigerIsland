package com.tigerisland;

import java.util.concurrent.BlockingQueue;

public class Messager implements Runnable {

    protected BlockingQueue outboundQueue;

    Messager(GlobalSettings globalSettings) {
        this.outboundQueue = globalSettings.outboundQueue;
    }

    public void run() {
        while(!Thread.interrupted()) {
            // Send waiting messages
        }
    }
}
