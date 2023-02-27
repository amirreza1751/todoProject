package com.example.demo.controllers;

import com.example.demo.DTO.TodoInsertDTO;
import com.example.demo.DTO.TodoDTO;
import com.example.demo.mapper.TodoMapper;
import com.example.demo.model.Todo;
import com.example.demo.services.TodoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
    TodoService todoService;
    public TodoMapper mapper;

    public TodoController(TodoService todoService, TodoMapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        List<Todo> todos = todoService.getTodos();
        return new ResponseEntity<>(mapper.toTodoDTO(todos), HttpStatus.OK);
    }
    @GetMapping({"/{todoId}"})
    public ResponseEntity<TodoDTO> getTodo(@PathVariable Long todoId) {
        return new ResponseEntity<>(mapper.toTodoDTO(todoService.getTodoById(todoId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TodoDTO> saveTodo(@RequestBody TodoInsertDTO todoInsertDTO) {
        Todo todo1 = todoService.insert(mapper.toTodo(todoInsertDTO));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("todo", "/api/v1/todo/" + todo1.getId().toString());
        return new ResponseEntity<>(mapper.toTodoDTO(todo1), httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{todoId}"})
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable("todoId") Long todoId, @RequestBody TodoInsertDTO todoInsertDTO) {
        todoService.updateTodo(todoId, mapper.toTodo(todoInsertDTO));
        return new ResponseEntity<>(mapper.toTodoDTO(todoService.getTodoById(todoId)), HttpStatus.OK);
    }
    @DeleteMapping({"/{todoId}"})
    public ResponseEntity<?> deleteTodo(@PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}