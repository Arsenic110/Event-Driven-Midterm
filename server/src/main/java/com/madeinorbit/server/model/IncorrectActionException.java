package model;

class IncorrectActionException extends Exception{
    public IncorrectActionException(String errorMessage) {
        super(errorMessage);
    }
}