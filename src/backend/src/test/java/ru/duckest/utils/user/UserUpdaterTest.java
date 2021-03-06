package ru.duckest.utils.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.entity.User;
import ru.duckest.repository.Users;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = UserUpdater.class)
class UserUpdaterTest {

    @Autowired
    private UserUpdater userUpdater;

    @MockBean
    private Users users;

    @Test()
    @DisplayName("Пользователь исправно сохраняется")
    void userCanBeSaved() {
        User validUser = UserEntityTestFactory.getValidUser();

        userUpdater.update(validUser);
        verify(users).save(validUser);
    }

}