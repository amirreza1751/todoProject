package com.example.demo.exception;

import com.example.demo.model.exception.NoSuchEntityExistsException;
import com.example.demo.model.exception.OperationNotPermittedException;
import com.example.demo.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NoSuchEntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleException(NoSuchEntityExistsException e){
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), e.getMessage()
        );
    }

    @ExceptionHandler(value = OperationNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleException(OperationNotPermittedException e){
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), e.getMessage()
        );
    }
}
