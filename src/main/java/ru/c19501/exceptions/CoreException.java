package ru.c19501.exceptions;

public class CoreException extends Exception {

    private final String message;

    public CoreException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CoreException: " + message;
    }
}
