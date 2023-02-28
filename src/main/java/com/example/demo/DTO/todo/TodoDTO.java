package com.example.demo.DTO.todo;

import com.example.demo.DTO.tag.TagDTO;
import com.example.demo.model.TodoStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
public class TodoDTO {
    Long id;
    String title;
    String description;
    TodoStatus todoStatus;
    Set<TagDTO> tagSet;
}
