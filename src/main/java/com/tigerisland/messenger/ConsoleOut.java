package com.tigerisland.messenger;

import java.util.concurrent.BlockingQueue;

public class ConsoleOut {

    public static void printClientMessage(String message) {
        System.out.println("CLIENT: " + message);
    }

    public static void printServerMessage(String message) {
        System.out.println("SERVER: " + message);
    }

    public static void printGameMessage(String gameID, String message) {
        System.out.println("GAME " + gameID + ": " + message);
    }

    public static void printGameMessage(String gameID, int moveID, String message) {
        System.out.println("GAME " + gameID + ": MOVE " + moveID + ", " + message);
    }

    public static void printGameMessageDebugging(String gameID, BlockingQueue<Message> inboundMessages) {
        for(Message message : inboundMessages) {
            System.out.println("\t" + message.toString() + "\t" + message.getMessageType().toString());
        }
    }

    public static void printGameMessages(String message) {
        System.out.println("\t" + message);
    }
}
