package com.tigerisland.game;

public class InvalidMoveException extends Exception {

    public InvalidMoveException() {}

    public InvalidMoveException(String message)
    {
        super(message);
    }
}
