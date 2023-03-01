package com.example.demo.DTO.user;

import com.example.demo.DTO.todo.TodoDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UsersDTO {
    Long id;
    String name;
    Set<TodoDTO> todoSet;
}
