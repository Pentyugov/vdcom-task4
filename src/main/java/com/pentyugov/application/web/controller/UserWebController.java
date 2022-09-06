package com.pentyugov.application.web.controller;

import com.pentyugov.application.core.dto.UserDto;
import com.pentyugov.application.core.service.FileService;
import com.pentyugov.application.web.payload.UserListDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class UserWebController {

    private static final String URL = "http://localhost:8080/api/v1/users";

    private final RestTemplate restTemplate;
    private final FileService fileService;

    @GetMapping
    public String main() {
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String users(Model model) {
        UserListDtoResponse response = restTemplate.getForObject(URL, UserListDtoResponse.class);
        model.addAttribute("users", response != null ? response.getUsers() : null);
        return "users";
    }

    @GetMapping("/users/add")
    public String userEditor(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("create", Boolean.TRUE);
        return "users-editor";
    }

    @GetMapping("/users/edit/{id}")
    public String userEditor(@PathVariable Long id, Model model) {
        UserDto response = restTemplate.getForObject(URL + "/" + id, UserDto.class);
        model.addAttribute("user", response);
        return "users-editor";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        restTemplate.delete(URL + "/" + id);
        return "redirect:/users";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute("user") UserDto userDto) {
        restTemplate.postForObject(URL, new HttpEntity<>(userDto), UserDto.class);
        return "redirect:/users";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") UserDto userDto) {
        restTemplate.put(URL, new HttpEntity<>(userDto), UserDto.class);
        return "redirect:/users";
    }

    @PostMapping("/users/upload-csv")
    public String importDataFromCsv(@RequestParam("file") MultipartFile file) {
        fileService.importFromCsv(file);
        return "redirect:/users";
    }

}
