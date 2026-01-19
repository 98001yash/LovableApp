package com.company.lovable.exceptions.ai;

public  abstract class AIException extends RuntimeException{

    public AIException(String message){
        super(message);
    }

    public AIException(String message,Throwable cause){
        super(message, cause);
    }
}
