package com.example.demo.mapper.user;

import com.example.demo.DTO.todo.TodoDTO;
import com.example.demo.DTO.user.UsersDTO;
import com.example.demo.model.Todo;
import com.example.demo.model.Users;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UsersMapper {
    public abstract Users toEntity(UsersDTO usersDTO);
    public abstract UsersDTO toDto(Users user);
    public abstract List<UsersDTO> toDTO (Collection<Users> users);
    @AfterMapping
    protected void addOwnerNameToTodoDTO(Users user, @MappingTarget UsersDTO usersDTO){
        if (usersDTO.getTodoSet().size() == 0)
            return;
        usersDTO.getTodoSet().forEach(todoDTO -> todoDTO.setOwner(user.getName()));
    }
}
