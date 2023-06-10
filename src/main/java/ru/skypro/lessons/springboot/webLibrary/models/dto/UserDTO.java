package ru.skypro.lessons.springboot.webLibrary.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Role;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {

    private Integer id;

    private String password;

    private String username;

    private boolean enabled;
    private List<Role> roles;
}
