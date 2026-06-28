package com.sunka.user.mapper;

import com.sunka.user.dto.UserDto;
import com.sunka.user.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
