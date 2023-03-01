package com.example.demo.services;

import com.example.demo.model.Todo;
import com.example.demo.model.Users;
import com.example.demo.model.exception.NoSuchEntityExistsException;
import com.example.demo.model.exception.OperationNotPermittedException;
import com.example.demo.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        return usersRepository.findById(id).orElseThrow(
                () -> new NoSuchEntityExistsException(Users.class.getSimpleName(), id)
        );
    }

    @Override
    public Users insert(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public void updateEntity(Long id, Users user) {
        Users userFromDb = usersRepository.findById(id).orElse(null);
        if (userFromDb == null) throw new NoSuchEntityExistsException(Users.class.getSimpleName(), id);
        else {
            userFromDb.setName(user.getName());
            usersRepository.save(userFromDb);
        }
    }

    @Override
    public void deleteEntity(Long userId) {
        if (userId == null) throw new NoSuchEntityExistsException(Users.class.getSimpleName(), null);
        else{
            Users userFromDb = usersRepository.findById(userId).orElse(null);
            if (userFromDb == null) throw new NoSuchEntityExistsException(Users.class.getSimpleName(), userId);
            else usersRepository.deleteById(userId);
        }
    }

    public Users assignTodos(Long userId, Long[] todoIds){
        Users user = getEntityById(userId);
        ArrayList<Todo> todos = todoService.findAllById(todoIds);
        if (todos.size()== 0) throw new NoSuchEntityExistsException(Todo.class.getSimpleName(), Arrays.toString(todoIds));
        todos.forEach(todo -> {
            if (todo.getUser() != null && todo.getUser().getId() != user.getId()) throw new OperationNotPermittedException(Todo.class.getSimpleName(), todo.getId());
        });
        todos.forEach(todo -> {
            todo.setUser(user);
            todoService.updateEntity(todo.getId(), todo);
            user.getTodoSet().add(todo);
        });
        return usersRepository.save(user);
    }

    public Users removeTodos(Long userId, Long[] todoIds){
        Users user = getEntityById(userId);
        ArrayList<Todo> todos = todoService.findAllById(todoIds);
        if (todos.size() == 0) throw new NoSuchEntityExistsException(Todo.class.getSimpleName(), Arrays.toString(todoIds));
        if (user.getTodoSet() == null) throw new RuntimeException();
        todos.forEach(todo -> {
            if (todo.getUser() == null || todo.getUser().getId() != userId) throw new OperationNotPermittedException(Todo.class.getSimpleName(), todo.getId());
            todo.setUser(null);
            todoService.updateEntity(todo.getId(), todo);
            user.getTodoSet().remove(todo);
        });
        return usersRepository.save(user);
    }
}