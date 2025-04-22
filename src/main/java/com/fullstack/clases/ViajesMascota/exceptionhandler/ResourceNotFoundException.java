package com.fullstack.clases.ViajesMascota.exceptionhandler;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
