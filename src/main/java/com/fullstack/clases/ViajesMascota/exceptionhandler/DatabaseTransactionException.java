package com.fullstack.clases.ViajesMascota.exceptionhandler;

public class DatabaseTransactionException extends RuntimeException {
    
    public DatabaseTransactionException(String message, Throwable cause) {
        super(message, cause);
     }
}
