package ru.duckest.utils.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.entity.User;
import ru.duckest.repository.Users;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = UserSaver.class)
class UserSaverTest {

    @Autowired
    private UserSaver userSaver;

    @MockBean
    private Users users;

    @Test()
    @DisplayName("Пользователь исправно сохраняется")
    void userCanBeSaved() {
        User validUser = UserEntityTestFactory.getValidUser();

        userSaver.save(validUser);
        verify(users).save(validUser);
    }
}