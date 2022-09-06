package com.pentyugov.application.core.service;

import com.pentyugov.application.core.dto.UserDto;
import com.pentyugov.application.core.entity.User;

import java.util.List;

public interface UserService {
    String NAME = "userService";

    List<User> getAllUsers();

    User getById(long id);

    void add(User user);

    void update(User user);

    void delete(long id);

    User convert(UserDto userDto);

    UserDto convert(User user);
}
