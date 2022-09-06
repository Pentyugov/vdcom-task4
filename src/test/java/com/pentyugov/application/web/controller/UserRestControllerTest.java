package com.pentyugov.application.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pentyugov.application.core.dto.UserDto;
import com.pentyugov.application.core.entity.User;
import com.pentyugov.application.core.repository.UserRepository;
import com.pentyugov.application.core.service.UserService;
import com.pentyugov.application.web.payload.UserListDtoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {

    private static final String URL = "/api/v1/users";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    private User user;
    private ObjectMapper mapper;

    private final Long id = 1L;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        user = User
                .builder()
                .id(id)
                .login("user")
                .password("user")
                .email("user@mail.com")
                .build();
    }

    @Test
    void getAll() throws Exception {

        List<User> savedUsers = List.of(user);

        when(userRepository.findAll()).thenReturn(savedUsers);
        String result = this.mockMvc
                .perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserListDtoResponse response = mapper.readValue(result, UserListDtoResponse.class);
        assertAll(
                () -> assertNotNull(response),
                () -> assertNotNull(response.getUsers())
        );
    }

    @Test
    void getById() throws Exception {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        String result = this.mockMvc
                .perform(MockMvcRequestBuilders.get(URL + "/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserDto userDto = mapper.readValue(result, UserDto.class);

        assertAll(
                () -> assertEquals(user.getId(), userDto.getId()),
                () -> assertEquals(user.getLogin(), userDto.getLogin()),
                () -> assertEquals(user.getPassword(), userDto.getPassword()),
                () -> assertEquals(user.getEmail(), userDto.getEmail())
        );

    }

    @Test
    void add() throws Exception {
        UserDto userDto = userService.convert(user);
        String content = mapper.writeValueAsString(userDto);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(URL)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(userRepository).save(user);
    }

    @Test
    void update() throws Exception {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserDto userDto = userService.convert(user);
        String content = mapper.writeValueAsString(userDto);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put(URL)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(userRepository).save(user);
    }

    @Test
    void delete() throws Exception {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(URL + "/" + id))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(userRepository).delete(user);
    }
}