package ru.skypro.lessons.springboot.webLibrary.models.mapper;

import ru.skypro.lessons.springboot.webLibrary.domains.entity.User;
import ru.skypro.lessons.springboot.webLibrary.models.dto.UserDTO;

public class UserDTOMapper {
    public static  User toUser(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .enabled(userDTO.isEnabled())
                .roles(userDTO.getRoles()).build();
    }
    public static UserDTO fromUser(User user){
        return UserDTO.builder()
                .id(user.getId())
                .password(user.getPassword())
                .username(user.getUsername())
                .enabled(user.isEnabled())
                .roles(user.getRoles()).build();
    }
}
