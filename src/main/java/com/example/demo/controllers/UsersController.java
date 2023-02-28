package com.example.demo.controllers;

import com.example.demo.DTO.user.UsersDTO;
import com.example.demo.mapper.user.UsersMapper;
import com.example.demo.model.Users;
import com.example.demo.services.UsersServiceImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UsersServiceImpl usersService;
    private final UsersMapper mapper;

    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
        this.mapper = Mappers.getMapper(UsersMapper.class);
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<Users> users = usersService.getEntities();
        return new ResponseEntity<>(mapper.toDTO(users), HttpStatus.OK);
    }
    @GetMapping({"/{userId}"})
    public ResponseEntity<UsersDTO> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(mapper.toDto((Users) usersService.getEntityById(userId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<UsersDTO> saveUser(@RequestBody UsersDTO usersDTO) {
        Users user1 = (Users) usersService.insert(mapper.toEntity(usersDTO));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("user", "/api/v1/users/" + user1.getId().toString());
        return new ResponseEntity<>(mapper.toDto(user1), httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{userId}"})
    public ResponseEntity<UsersDTO> updateUser(@PathVariable("userId") Long userId, @RequestBody UsersDTO usersDTO) {
        usersService.updateEntity(userId, mapper.toEntity(usersDTO));
        return new ResponseEntity<>(mapper.toDto((Users) usersService.getEntityById(userId)), HttpStatus.OK);
    }
    @DeleteMapping({"/{userId}"})
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        usersService.deleteEntity(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping({"/{userId}/assign-todo"})
    public ResponseEntity<UsersDTO> assignTodos(@PathVariable("userId") Long userId, @RequestBody Long[] ids) {
        Users user1 = usersService.assignTodos(userId, ids);
        return new ResponseEntity<>(mapper.toDto(user1), HttpStatus.OK);
    }

    @PostMapping({"/{userId}/remove-todo"})
    public ResponseEntity<UsersDTO> removeTodos(@PathVariable("userId") Long userId, @RequestBody Long[] ids) {
        Users user1 = usersService.removeTodos(userId, ids);
        return new ResponseEntity<>(mapper.toDto(user1), HttpStatus.OK);
    }
}