package com.example.demo.mapper.todo;

import com.example.demo.DTO.todo.TodoDTO;
import com.example.demo.model.Todo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TodoMapper {
     public abstract TodoDTO toDTO(Todo todo);

     public abstract List<TodoDTO> toDTO (Collection<Todo> todos);

     public abstract Todo toEntity(TodoDTO todoDTO);

     @AfterMapping
     protected void addOwnerNameToTodoDTO(Todo todo, @MappingTarget TodoDTO todoDTO){
          if (todo.getUser() == null)
               return;
          todoDTO.setOwner(todo.getUser().getName());
     }

}
