package ru.duckest.duckest.utils.user;

import ru.duckest.duckest.entity.User;

import java.util.UUID;

import static ru.duckest.duckest.utils.user.Constants.DUMMY_FIRST_NAME;
import static ru.duckest.duckest.utils.user.Constants.DUMMY_LAST_NAME;
import static ru.duckest.duckest.utils.user.Constants.DUMMY_MIDDLE_NAME;
import static ru.duckest.duckest.utils.user.Constants.VALID_EMAIL;

public class UserEntityTestFactory {

    public static User getValidUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .login(VALID_EMAIL)
                .firstName(DUMMY_FIRST_NAME)
                .lastName(DUMMY_LAST_NAME)
                .middleName(DUMMY_MIDDLE_NAME)
                .build();
    }
}