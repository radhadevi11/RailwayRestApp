package com.radha.railwayrest.app.service;

public class NoSuchTrainException extends RuntimeException{
    public NoSuchTrainException(int trainId) {
        super("No such train having the Id : "+trainId);
    }
}
