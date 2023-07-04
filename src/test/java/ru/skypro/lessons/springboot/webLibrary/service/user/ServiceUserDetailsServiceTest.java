package ru.skypro.lessons.springboot.webLibrary.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Report;
import ru.skypro.lessons.springboot.webLibrary.models.mapper.UserDTOMapper;
import ru.skypro.lessons.springboot.webLibrary.repository.UserRepository;
import ru.skypro.lessons.springboot.webLibrary.security.SecurityUserDetails;

import java.util.Optional;

import static constants.TestConstances.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ServiceUserDetailsServiceTest {

    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private SecurityUserDetailsService out;

    @DisplayName("Return user details on correct username and call repository once")
    @Test
    @SneakyThrows
    void loadUserByUsername_CorrectName_ShouldCallRepositoryOnce() {
        when(userRepositoryMock.findByUsername(eq(CORRECT_USERNAME_TEST)))
                .thenReturn(Optional.of(USER_TEST));

        UserDetails actual = out.loadUserByUsername(CORRECT_USERNAME_TEST);
        UserDetails expected = new SecurityUserDetails(UserDTOMapper.fromUser(USER_TEST));

        assertEquals(expected,actual);

        verify(userRepositoryMock, times(1)).findByUsername(CORRECT_USERNAME_TEST);

    }
    @DisplayName("Throw exception if username was wrong and call repository once")
    @Test
    @SneakyThrows
    void loadUserByUsername_NotCorrectName_ShouldThrowException_ShouldCallRepositoryOnce() {
        when(userRepositoryMock.findByUsername(eq(NOT_CORRECT_USERNAME_TEST)))
                .thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class,() -> out.loadUserByUsername(NOT_CORRECT_USERNAME_TEST));

        verify(userRepositoryMock, times(1)).findByUsername(NOT_CORRECT_USERNAME_TEST);

    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .map(UserDTOMapper::fromUser)
//                .map(SecurityUserDetails::new)
//                .orElseThrow(()-> new UsernameNotFoundException(String.format("Username %s not found",username)));
//    }
}