package ru.duckest.duckest.service;

import ru.duckest.duckest.dto.UserDto;

public interface UserService {

    void save(UserDto user);

    UserDto getUserBy(String email);

    void update(UserDto user);
}
