package com.example.telexpress.controller.errormanage;

// Para 404
public class ResourceNotFoundException  extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
