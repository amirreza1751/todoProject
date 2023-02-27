package com.example.demo.services;

import com.example.demo.model.Todo;
import com.example.demo.model.Users;
import com.example.demo.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements GenericService<Users> {
    private final UsersRepository usersRepository;
    private final TodoServiceImpl todoService;

    public UsersServiceImpl(UsersRepository usersRepository, TodoServiceImpl todoService) {
        this.usersRepository = usersRepository;
        this.todoService = todoService;
    }

    @Override
    public List<Users> getEntities() {
        List<Users> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public Users getEntityById(Long id) {
        return usersRepository.findById(id).get();
    }

    @Override
    public Users insert(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public void updateEntity(Long id, Users user) {
        Users userFromDb = usersRepository.findById(id).get();
        System.out.println(userFromDb);
        userFromDb.setName(user.getName());
        usersRepository.save(userFromDb);
    }

    @Override
    public void deleteEntity(Long userId) {
        usersRepository.deleteById(userId);
    }

    public void assignTodos(Long userId, Long[] todoIds){
        Users user = getEntityById(userId);
        ArrayList<Todo> todos = todoService.findAllById(todoIds);
        if (todos.size()!= 0){
            todos.forEach(todo -> {
                todo.setUser(user);
            });
            user.getTodoSet().addAll(todos);
            usersRepository.save(user);
            todoService.saveAll(todos);
        }
    }
}