package com.guillermo.DeadByDaylightAPI.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(long id) {
        super("element not found: " + id);
    }
}
