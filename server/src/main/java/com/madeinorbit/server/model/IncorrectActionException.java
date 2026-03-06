package com.madeinorbit.server.model;

public class IncorrectActionException extends Exception{
    public IncorrectActionException(String errorMessage) {
        super(errorMessage);
    }
}