package com.pentyugov.application.core;

import com.pentyugov.application.core.entity.User;
import com.pentyugov.application.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Lazy(false)
@Component
@RequiredArgsConstructor
public class ApplicationStartedListener {

    private final UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
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

        userService.add(admin);
        userService.add(user);
    }
}
