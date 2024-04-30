package com.cognizant.exceptions;

public class EmiPlanLimitExceededException extends RuntimeException {
    public EmiPlanLimitExceededException(String message) {
        super(message);
    }
}
