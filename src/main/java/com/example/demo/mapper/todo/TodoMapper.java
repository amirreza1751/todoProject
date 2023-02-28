package com.example.demo.mapper.todo;

import com.example.demo.DTO.todo.TodoInsertDTO;
import com.example.demo.DTO.todo.TodoDTO;
import com.example.demo.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TodoMapper {
     public abstract TodoDTO toDTO(Todo todo);

     public abstract List<TodoDTO> toDTO (Collection<Todo> todos);

     public abstract Todo toEntity(TodoDTO todoDTO);

}
