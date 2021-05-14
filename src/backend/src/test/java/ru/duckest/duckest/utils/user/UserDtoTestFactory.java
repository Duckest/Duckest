package ru.duckest.duckest.utils.user;

import ru.duckest.duckest.dto.UserDto;

import static ru.duckest.duckest.utils.user.Constants.DUMMY_FIRST_NAME;
import static ru.duckest.duckest.utils.user.Constants.DUMMY_LAST_NAME;
import static ru.duckest.duckest.utils.user.Constants.DUMMY_MIDDLE_NAME;
import static ru.duckest.duckest.utils.user.Constants.EMAIL_FOR_NON_EXISTENT_USER;
import static ru.duckest.duckest.utils.user.Constants.VALID_EMAIL;

public class UserDtoTestFactory {

    public static UserDto getValidUser() {
        return UserDto.builder()
                .email(VALID_EMAIL)
                .firstName(DUMMY_FIRST_NAME)
                .lastName(DUMMY_LAST_NAME)
                .middleName(DUMMY_MIDDLE_NAME)
                .build();
    }

    public static UserDto getInvalidUser() {
        return UserDto.builder()
                .email(EMAIL_FOR_NON_EXISTENT_USER)
                .firstName(DUMMY_FIRST_NAME)
                .lastName(DUMMY_LAST_NAME)
                .middleName(DUMMY_MIDDLE_NAME)
                .build();
    }

    public static UserDto getUserForUpdate() {
        return UserDto.builder()
                .email(VALID_EMAIL)
                .firstName("DUMMY_UPDATE_NAME")
                .lastName("DUMMY_UPDATE_LAST_NAME")
                .middleName("DUMMY_UPDATE_MIDDLE_NAME")
                .build();
    }

}
