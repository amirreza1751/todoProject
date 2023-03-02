package com.example.demo.services;

import com.example.demo.model.Tag;
import com.example.demo.model.Todo;
import com.example.demo.model.exception.NoSuchEntityExistsException;
import com.example.demo.repositories.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl extends GenericService<Todo> {
    private final TodoRepository todoRepository;
    private final TagServiceImpl tagService;

    public TodoServiceImpl(TodoRepository todoRepository, TagServiceImpl tagService) {
        this.todoRepository = todoRepository;
        this.tagService = tagService;
    }

    @Override
    public Page<Todo> getEntities(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable page = getSortingPageable(pageNo, pageSize, sortBy, sortDir);
        return todoRepository.findAll(page);
    }

    @Override
    public Todo getEntityById(Long id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new NoSuchEntityExistsException(Todo.class.getSimpleName(), id)
        );
    }

    @Override
    public Todo insert(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void updateEntity(Long id, Todo todo) {
        Todo todoFromDb = todoRepository.findById(id).orElse(null);
        if (todoFromDb == null) throw new NoSuchEntityExistsException(Todo.class.getSimpleName(), id);
        todoFromDb.setTodoStatus(todo.getTodoStatus());
        todoFromDb.setDescription(todo.getDescription());
        todoFromDb.setTitle(todo.getTitle());
        todoRepository.save(todoFromDb);
    }

    @Override
    public void deleteEntity(Long todoId) {
        if (todoId == null) throw new NoSuchEntityExistsException(Todo.class.getSimpleName(), null);
        Todo todoFromDb = todoRepository.findById(todoId).orElse(null);
        if (todoFromDb == null) throw new NoSuchEntityExistsException(Todo.class.getSimpleName(), todoId);
        else{
            Long[] tagIdsToRemove = todoFromDb.getTagSet().stream().map(Tag::getId).collect(Collectors.toSet()).toArray(new Long[0]);
            removeTags(todoId, tagIdsToRemove);
            todoRepository.deleteById(todoId);
        }
    }

    public ArrayList<Todo> findAllById(Long[] ids){
        return (ArrayList<Todo>) todoRepository.findAllById(List.of(ids));
    }

    public Iterable<Todo> saveAll(Iterable<Todo> todos){
        return todoRepository.saveAll(todos);
    }

    public Todo assignTags(Long todoId, Long[] tagIds){
        Todo todo = getEntityById(todoId);
        ArrayList<Tag> tags = tagService.findAllById(tagIds);
        if (tags.size()== 0) throw new NoSuchEntityExistsException(Tag.class.getSimpleName(), Arrays.toString(tagIds));
        tags.forEach(tag -> tag.getTodoSet().add(todo));
        todo.getTagSet().addAll(tags);
        tagService.saveAll(tags);
        return todoRepository.save(todo);
    }

    public Todo removeTags(Long todoId, Long[] tagIds){
        Todo todo = getEntityById(todoId);
        ArrayList<Tag> tags = tagService.findAllById(tagIds);
        if (tags.size()== 0) throw new NoSuchEntityExistsException(Tag.class.getSimpleName(), Arrays.toString(tagIds));
        todo.getTagSet().removeAll(tags);
        tags.forEach(tag -> tag.getTodoSet().remove(todo));
        tagService.saveAll(tags);
        return todoRepository.save(todo);
    }
}