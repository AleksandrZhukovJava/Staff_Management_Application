package ru.skypro.lessons.springboot.webLibrary.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.lessons.springboot.webLibrary.models.dto.UserDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
public class SecurityUserDetails implements UserDetails {
    private UserDTO userDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(userDTO)
                .map(UserDTO::getRoles)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getTitle())).toList();
    }

    @Override
    public String getPassword() {
        return Optional.ofNullable(userDTO).map(UserDTO::getPassword).orElse(null);
    }

    @Override
    public String getUsername() {
        return Optional.ofNullable(userDTO).map(UserDTO::getUsername).orElse(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Optional.ofNullable(userDTO).map(UserDTO::isEnabled).orElse(true);
    }
}
