package com.example.telexpress.controller.errormanage;

// Para 429
public class TooManyRequestsException  extends RuntimeException{
    public TooManyRequestsException(String message) {
        super(message);
    }
}
