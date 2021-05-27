package ru.duckest.utils.user;

import ru.duckest.dto.UserDto;

public class UserDtoTestFactory {

    public static UserDto getValidUser() {
        return UserDto.builder()
                .email(Constants.VALID_EMAIL)
                .firstName(Constants.DUMMY_FIRST_NAME)
                .lastName(Constants.DUMMY_LAST_NAME)
                .middleName(Constants.DUMMY_MIDDLE_NAME)
                .build();
    }

    public static UserDto getInvalidUser() {
        return UserDto.builder()
                .email(Constants.EMAIL_FOR_NON_EXISTENT_USER)
                .firstName(Constants.DUMMY_FIRST_NAME)
                .lastName(Constants.DUMMY_LAST_NAME)
                .middleName(Constants.DUMMY_MIDDLE_NAME)
                .build();
    }

    public static UserDto getUserForUpdate() {
        return UserDto.builder()
                .email(Constants.VALID_EMAIL)
                .firstName("DUMMY_UPDATE_NAME")
                .lastName("DUMMY_UPDATE_LAST_NAME")
                .middleName("DUMMY_UPDATE_MIDDLE_NAME")
                .build();
    }

}
