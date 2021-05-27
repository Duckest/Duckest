package ru.duckest.utils.user;

import ru.duckest.entity.User;

import java.util.UUID;

public class UserEntityTestFactory {

    private static final UUID USER_ID = UUID.fromString("3d50f1dd-be9b-40e9-8b88-ab2b2377e6bd");

    public static User getValidUser() {
        return User.builder()
                .id(USER_ID)
                .email(Constants.VALID_EMAIL)
                .firstName(Constants.DUMMY_FIRST_NAME)
                .lastName(Constants.DUMMY_LAST_NAME)
                .middleName(Constants.DUMMY_MIDDLE_NAME)
                .build();
    }
}
