package com.pentyugov.application.core.service.impl;

import com.pentyugov.application.core.dto.UserDto;
import com.pentyugov.application.core.entity.User;
import com.pentyugov.application.core.exception.EntityNotFoundException;
import com.pentyugov.application.core.repository.UserRepository;
import com.pentyugov.application.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(UserService.NAME)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public User getById(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        User userToUpdate = getById(user.getId());
        userToUpdate.setLogin(user.getLogin());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());
        userRepository.save(userToUpdate);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(getById(id));
    }

    @Override
    public User convert(UserDto userDto) {
        return User
                .builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .build();
    }

    @Override
    public UserDto convert(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
