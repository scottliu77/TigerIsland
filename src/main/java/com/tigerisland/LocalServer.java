package com.tigerisland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalServer implements Runnable {

    private ServerSocket serverSocket;

    LocalServer(GlobalSettings globalSettings) {
        try {
            this.serverSocket = new ServerSocket(globalSettings.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(!Thread.interrupted()) {
            try {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printMessagesToConsole(reader.readLine());
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    public void printMessagesToConsole(String message) {
        System.out.println(message);
    }
}
