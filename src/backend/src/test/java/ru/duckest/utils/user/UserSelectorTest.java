package ru.duckest.utils.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.entity.User;
import ru.duckest.repository.Users;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserSelector.class)
class UserSelectorTest {

    @Autowired
    private UserSelector userSelector;

    @MockBean
    private Users users;

    @Test
    @DisplayName("Если пользователя с таким email не существует, то бросается исключение")
    void emailForNonExistentUserCauseException() {
        when(users.findUsersByEmail(Constants.EMAIL_FOR_NON_EXISTENT_USER)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> userSelector.getUserBy(Constants.EMAIL_FOR_NON_EXISTENT_USER));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Существующий пользователь находится по валидному email исправно")
    void userCanBeObtainedByValidEmail() {
        User validUser = UserEntityTestFactory.getValidUser();

        when(users.findUsersByEmail(Constants.VALID_EMAIL)).thenReturn(Optional.of(validUser));

        User actual = userSelector.getUserBy(Constants.VALID_EMAIL);
        assertThat(actual).isEqualTo(validUser);
    }

}