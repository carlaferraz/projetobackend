package br.pucpr.projetobackend.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, String details) {
        super(message + ": " + details);
    }
}

