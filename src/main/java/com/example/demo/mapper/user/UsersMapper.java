package com.example.demo.mapper.user;

import com.example.demo.DTO.user.UsersDTO;
import com.example.demo.model.Users;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UsersMapper {
    public abstract Users toUsers(UsersDTO usersDTO);
    public abstract UsersDTO toDto(Users user);
    public abstract List<UsersDTO> toDTO (Collection<Users> users);
}
