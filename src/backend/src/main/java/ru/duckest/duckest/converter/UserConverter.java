package ru.duckest.duckest.converter;

import org.springframework.stereotype.Component;
import ru.duckest.duckest.dto.UserDto;
import ru.duckest.duckest.entity.User;

@Component
public class UserConverter {

    public User convert(UserDto user) {
        return User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .build();
    }

    public UserDto convert(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .build();
    }
}
