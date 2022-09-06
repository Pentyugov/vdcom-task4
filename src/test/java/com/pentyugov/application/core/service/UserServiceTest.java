package com.pentyugov.application.core.service;

import com.pentyugov.application.core.dto.UserDto;
import com.pentyugov.application.core.entity.User;
import com.pentyugov.application.core.exception.EntityNotFoundException;
import com.pentyugov.application.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;
    private final Long id = 1L;

    @BeforeEach
    void setUp() {
        user = User
                .builder()
                .id(id)
                .login("user")
                .password("user")
                .email("user@mail.com")
                .build();
    }

    @Test
    void getAllUsers() {
        User admin = User
                .builder()
                .login("admin")
                .password("admin")
                .email("admin@mail.com")
                .build();
        User user = User
                .builder()
                .login("user")
                .password("user")
                .email("user@mail.com")
                .build();

        List<User> savedUsers = List.of(admin, user);
        when(userRepository.findAll()).thenReturn(savedUsers);
        List<User> foundedUsers = userService.getAllUsers();
        assertIterableEquals(savedUsers, foundedUsers);

    }

    @Test
    void getById() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        User foundedUser = userService.getById(id);
        assertEquals(user, foundedUser);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getById(id));
    }

    @Test
    void add() {
        userService.add(user);
        verify(userRepository).save(user);
    }

    @Test
    void update() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.update(user);
        verify(userRepository).save(user);
    }

    @Test
    void delete() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.delete(user.getId());
        verify(userRepository).delete(user);
    }

    @Test
    void convertFromUser() {
        UserDto userDto = userService.convert(user);
        assertAll(
                () -> assertEquals(user.getId(), userDto.getId()),
                () -> assertEquals(user.getLogin(), userDto.getLogin()),
                () -> assertEquals(user.getPassword(), userDto.getPassword()),
                () -> assertEquals(user.getEmail(), userDto.getEmail())
        );
    }

    @Test
    void convertFromUserDto() {
        UserDto userDto = UserDto
                .builder()
                .id(id)
                .login("user")
                .password("user")
                .email("user@mail.com")
                .build();
        User user = userService.convert(userDto);
        assertAll(
                () -> assertEquals(user.getId(), userDto.getId()),
                () -> assertEquals(user.getLogin(), userDto.getLogin()),
                () -> assertEquals(user.getPassword(), userDto.getPassword()),
                () -> assertEquals(user.getEmail(), userDto.getEmail())
        );
    }

}