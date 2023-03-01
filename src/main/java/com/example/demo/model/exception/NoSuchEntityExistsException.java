package com.example.demo.model.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NoSuchEntityExistsException extends RuntimeException{
    private String message;

    public NoSuchEntityExistsException(String entityName, Object id) {
        super("Entity not found!");
        this.message = entityName + " was not found for id: " + id + "!";
    }
}
