package com.example.demo.services;

import com.example.demo.model.Tag;
import com.example.demo.model.Todo;
import com.example.demo.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements GenericService<Todo> {
    private final TodoRepository todoRepository;
    private final TagServiceImpl tagService;

    public TodoServiceImpl(TodoRepository todoRepository, TagServiceImpl tagService) {
        this.todoRepository = todoRepository;
        this.tagService = tagService;
    }

    @Override
    public List<Todo> getEntities() {
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todos::add);
        return todos;
    }

    @Override
    public Todo getEntityById(Long id) {
        return todoRepository.findById(id).get();
    }

    @Override
    public Todo insert(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void updateEntity(Long id, Todo todo) {
        Todo todoFromDb = todoRepository.findById(id).get();
        System.out.println(todoFromDb.toString());
        todoFromDb.setTodoStatus(todo.getTodoStatus());
        todoFromDb.setDescription(todo.getDescription());
        todoFromDb.setTitle(todo.getTitle());
        todoRepository.save(todoFromDb);
    }

    @Override
    public void deleteEntity(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    public ArrayList<Todo> findAllById(Long[] ids){
        return (ArrayList<Todo>) todoRepository.findAllById(List.of(ids));
    }

    public Iterable<Todo> saveAll(Iterable<Todo> todos){
        return todoRepository.saveAll(todos);
    }

    public void assignTags(Long todoId, Long[] tagsIds){
        Todo todo = getEntityById(todoId);
        ArrayList<Tag> tags = tagService.findAllById(tagsIds);
        if (tags.size()!= 0){
            tags.forEach(tag -> {
                tag.getTodoSet().add(todo);
            });
            todo.getTagSet().addAll(tags);
            tagService.saveAll(tags);
            updateEntity(todo.getId(), todo);
        }
    }
}