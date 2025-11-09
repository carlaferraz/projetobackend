package br.pucpr.projetobackend.exception;

public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
    
    public DuplicateResourceException(String message, String details) {
        super(message + ": " + details);
    }
}

