package com.example.demo.services;

import com.example.demo.model.Todo;
import com.example.demo.model.Users;
import com.example.demo.model.exception.NoSuchEntityExistsException;
import com.example.demo.model.exception.OperationNotPermittedException;
import com.example.demo.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl extends GenericService<Users> {
    private final UsersRepository usersRepository;
    private final TodoServiceImpl todoService;

    public UsersServiceImpl(UsersRepository usersRepository, TodoServiceImpl todoService) {
        this.usersRepository = usersRepository;
        this.todoService = todoService;
    }

    @Override
    public Page<Users> getEntities(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable page = getSortingPageable(pageNo, pageSize, sortBy, sortDir);
        return usersRepository.findAll(page);
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
            else {
                Long[] todoIdsToRemove = userFromDb.getTodoSet().stream().map(Todo::getId).collect(Collectors.toSet()).toArray(new Long[0]);
                removeTodos(userId, todoIdsToRemove);
                usersRepository.deleteById(userId);
            }
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