package ru.duckest.duckest.utils.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.entity.User;
import ru.duckest.duckest.repository.Users;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;
import static ru.duckest.duckest.utils.user.Constants.EMAIL_FOR_NON_EXISTENT_USER;
import static ru.duckest.duckest.utils.user.Constants.VALID_EMAIL;

@SpringBootTest(classes = UserSelector.class)
class UserSelectorTest {

    @Autowired
    private UserSelector userSelector;

    @MockBean
    private Users users;

    @Test
    @DisplayName("Если пользователя с таким email не существует, то бросается исключение")
    void emailForNonExistentUserCauseException() {
        when(users.findUsersByEmail(EMAIL_FOR_NON_EXISTENT_USER)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> userSelector.getUserBy(EMAIL_FOR_NON_EXISTENT_USER));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Существующий пользователь находится по валидному email исправно")
    void userCanBeObtainedByValidEmail() {
        User validUser = UserEntityTestFactory.getValidUser();

        when(users.findUsersByEmail(VALID_EMAIL)).thenReturn(Optional.of(validUser));

        User actual = userSelector.getUserBy(VALID_EMAIL);
        assertThat(actual).isEqualTo(validUser);
    }

}