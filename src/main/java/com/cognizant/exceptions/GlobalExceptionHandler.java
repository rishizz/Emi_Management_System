package com.cognizant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmiPlanLimitExceededException.class})
    public ResponseEntity<Object> handleEmiPlanLimitExceededException(EmiPlanLimitExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler({InvalidPasswordException.class})
    public  ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler({LoanPaymentCompletedException.class})
    public  ResponseEntity<Object> handleLoanPaymentCompletedException(LoanPaymentCompletedException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler({UserNameIsAlreadyPresentException.class})
    public  ResponseEntity<Object> handleUserNameIsAlreadyPresentException(UserNameIsAlreadyPresentException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler({UsernameNotFoundException.class})
    public  ResponseEntity<Object> handleUserNameNotFoundException(UsernameNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler({LoanPlanIdNotDistinctException.class})
    public ResponseEntity<Object> handleLoanPlanIdNotDistinctException(LoanPlanIdNotDistinctException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

}