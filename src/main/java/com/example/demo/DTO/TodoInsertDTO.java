package com.example.demo.DTO;

import com.example.demo.model.TodoStatus;
import lombok.Data;

@Data
public class TodoInsertDTO {
    String title;
    String description;
    TodoStatus todoStatus;
}
