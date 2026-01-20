package com.company.lovable.exceptions.ai;

public class AiProviderUnavailableException extends AIException{


    public AiProviderUnavailableException(String message, OpenAiApiException cause) {
        super(message, cause);
    }
}
