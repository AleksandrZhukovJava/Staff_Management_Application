package ru.skypro.lessons.springboot.webLibrary.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.webLibrary.models.mapper.UserDTOMapper;
import ru.skypro.lessons.springboot.webLibrary.repository.UserRepository;
import ru.skypro.lessons.springboot.webLibrary.security.SecurityUserDetails;

@Service
@AllArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserDTOMapper::fromUser)
                .map(SecurityUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("Username %s not found",username)));
    }
}
