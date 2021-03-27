package com.guillermo.DeadByDaylightAPI.exceptions;

public class SurvivorNotFoundException extends RuntimeException{
    public SurvivorNotFoundException() {
        super();
    }

    public SurvivorNotFoundException(String message) {
        super(message);
    }

    public SurvivorNotFoundException(long id) {
        super("Survivor not found: " + id);
    }
}
