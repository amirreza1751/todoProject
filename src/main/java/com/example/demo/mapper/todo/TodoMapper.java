package com.example.demo.mapper.todo;

import com.example.demo.DTO.todo.TodoInsertDTO;
import com.example.demo.DTO.todo.TodoDTO;
import com.example.demo.model.Todo;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TodoMapper {

     public abstract TodoDTO toTodoDTO(Todo todo);
     public abstract Todo toTodo(TodoDTO dto);

     public abstract List<TodoDTO> toTodoDTO (Collection<Todo> todos);

     public abstract Todo toTodo(TodoInsertDTO todoInsertDTO);
}
