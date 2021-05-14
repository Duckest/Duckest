package ru.duckest.duckest.service.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.converter.UserConverter;
import ru.duckest.duckest.dto.UserDto;
import ru.duckest.duckest.entity.User;
import ru.duckest.duckest.service.UserService;
import ru.duckest.duckest.utils.user.UserDtoTestFactory;
import ru.duckest.duckest.utils.user.UserEntityTestFactory;
import ru.duckest.duckest.utils.user.UserSaver;
import ru.duckest.duckest.utils.user.UserSelector;
import ru.duckest.duckest.utils.user.UserUpdater;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.duckest.duckest.utils.user.Constants.EMAIL_FOR_NON_EXISTENT_USER;
import static ru.duckest.duckest.utils.user.Constants.INVALID_EMAIL;
import static ru.duckest.duckest.utils.user.Constants.VALID_EMAIL;
import static ru.duckest.duckest.utils.user.UserDtoTestFactory.getInvalidUser;
import static ru.duckest.duckest.utils.user.UserDtoTestFactory.getUserForUpdate;


@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {

    private final UserDto validUser = UserDtoTestFactory.getValidUser();
    private final User validUserEntity = UserEntityTestFactory.getValidUser();

    @Autowired
    private UserService service;

    @MockBean
    private UserConverter converter;


    @MockBean
    private UserSelector userSelector;
    @MockBean
    private UserSaver userSaver;
    @MockBean
    private UserUpdater userUpdater;

    @Test
    @DisplayName("Dto конвертируется и сохраняется исправно")
    void dtoConvertingAndSaving() {
        when(converter.convert(validUser)).thenReturn(validUserEntity);

        service.save(validUser);
        verify(userSaver).save(validUserEntity);
    }

    @Test
    @DisplayName("Если пользователя с таким email не существует, то бросается исключение")
    void emailForNonExistentUserCauseExceptionWhenGettingByEmail() {
        when(userSelector.getUserBy(EMAIL_FOR_NON_EXISTENT_USER)).thenThrow(IllegalArgumentException.class);

        Throwable throwable = catchThrowable(() -> service.getUserBy(EMAIL_FOR_NON_EXISTENT_USER));
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Если email невалиден, то бросается исключение")
    void invalidEmailCauseExceptionWhenGettingByEmail() {
        Throwable throwable = catchThrowable(() -> service.getUserBy(INVALID_EMAIL));
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Пользователь находится по валидному email")
    void userCanBeFoundByEmail() {
        when(userSelector.getUserBy(VALID_EMAIL)).thenReturn(validUserEntity);
        when(converter.convert(validUserEntity)).thenReturn(validUser);

        UserDto actual = service.getUserBy(VALID_EMAIL);
        assertThat(actual).isEqualTo(validUser);
    }

    @Test
    @DisplayName("Для несуществующего пользователя бросается исключение")
    void nonExistentUserCauseException() {
        UserDto invalidUser = getInvalidUser();
        when(userSelector.getUserBy(invalidUser.getEmail())).thenThrow(IllegalArgumentException.class);

        Throwable throwable = catchThrowable(() -> service.update(invalidUser));
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Dto конвертируется и обновляется исправно")
    void dtoConvertingAndUpdating() {
        UserDto userForUpdate = getUserForUpdate();
        when(userSelector.getUserBy(validUser.getEmail())).thenReturn(validUserEntity);

        service.update(userForUpdate);
        verify(userUpdater).update(validUserEntity);
    }
}