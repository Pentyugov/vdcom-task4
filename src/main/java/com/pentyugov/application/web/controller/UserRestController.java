package com.pentyugov.application.web.controller;

import com.pentyugov.application.core.dto.UserDto;
import com.pentyugov.application.core.service.UserService;
import com.pentyugov.application.web.payload.UserListDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(userService::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new UserListDtoResponse(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        UserDto userDto = userService.convert(userService.getById(id));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody UserDto userDto) {
        userService.add(userService.convert(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody UserDto userDto) {
        userService.update(userService.convert(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
