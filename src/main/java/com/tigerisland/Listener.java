package com.tigerisland;

import java.util.concurrent.BlockingQueue;

public class Listener implements Runnable {

    protected BlockingQueue inboundQueue;

    Listener(GlobalSettings globalSettings) {
        this.inboundQueue = globalSettings.inboundQueue;
    }

    public void run() {
        while(!Thread.interrupted()) {
            // Listen for messages
        }
        return;
    }
}