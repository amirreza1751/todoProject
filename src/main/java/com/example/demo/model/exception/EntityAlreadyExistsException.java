package com.example.demo.model.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EntityAlreadyExistsException extends RuntimeException{
    private String message;

    public EntityAlreadyExistsException(String entityName, Object id) {
        super("Entity not found!");
        this.message = entityName + " already exists with id: " + id + "!";
    }
}
