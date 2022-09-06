package com.pentyugov.application.web.payload;

import com.pentyugov.application.core.dto.UserDto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDtoResponse {
    private List<UserDto> users;
}
