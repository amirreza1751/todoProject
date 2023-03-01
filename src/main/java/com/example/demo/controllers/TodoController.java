package com.example.demo.controllers;

import com.example.demo.DTO.todo.TodoDTO;
import com.example.demo.DTO.user.UsersDTO;
import com.example.demo.mapper.todo.TodoMapper;
import com.example.demo.model.Todo;
import com.example.demo.services.TodoServiceImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
    private final TodoServiceImpl todoService;
    private final TodoMapper mapper;

    public TodoController(TodoServiceImpl todoService) {
        this.todoService = todoService;
        this.mapper = Mappers.getMapper(TodoMapper.class);
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        List<Todo> todos = todoService.getEntities();
        return new ResponseEntity<>(mapper.toDTO(todos), HttpStatus.OK);
    }
    @GetMapping({"/{todoId}"})
    public ResponseEntity<TodoDTO> getTodo(@PathVariable Long todoId) {
        return new ResponseEntity<>(mapper.toDTO((Todo) todoService.getEntityById(todoId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TodoDTO> saveTodo(@RequestBody TodoDTO todoDTO) {
        Todo todo1 = (Todo) todoService.insert(mapper.toEntity(todoDTO));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("todo", "/api/v1/todo/" + todo1.getId().toString());
        return new ResponseEntity<>(mapper.toDTO(todo1), httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{todoId}"})
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable("todoId") Long todoId, @RequestBody TodoDTO todoDTO) {
        todoService.updateEntity(todoId, mapper.toEntity(todoDTO));
        return new ResponseEntity<>(mapper.toDTO((Todo) todoService.getEntityById(todoId)), HttpStatus.OK);
    }
    @DeleteMapping({"/{todoId}"})
    public ResponseEntity<?> deleteTodo(@PathVariable("todoId") Long todoId) {
        todoService.deleteEntity(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping({"/{todoId}/assign-tags"})
    public ResponseEntity<TodoDTO> assignTags(@PathVariable("todoId") Long todoId, @RequestBody Long[] ids) {
        Todo todo = todoService.assignTags(todoId, ids);
        return new ResponseEntity<>(mapper.toDTO(todo), HttpStatus.OK);
    }
    @PostMapping({"/{todoId}/remove-tags"})
    public ResponseEntity<TodoDTO> removeTags(@PathVariable("todoId") Long todoId, @RequestBody Long[] ids) {
        Todo todo = todoService.removeTags(todoId, ids);
        return new ResponseEntity<>(mapper.toDTO(todo), HttpStatus.OK);
    }
}