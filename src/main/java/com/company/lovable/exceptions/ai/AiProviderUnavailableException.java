package com.company.lovable.exceptions.ai;

public class AiProviderUnavailableException extends AIException{


    public AiProviderUnavailableException(String message, RuntimeException cause) {
        super(message, cause);
    }
}
