package ru.duckest.duckest.utils.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.entity.User;
import ru.duckest.duckest.repository.Users;

import static org.mockito.Mockito.verify;
import static ru.duckest.duckest.utils.user.UserEntityTestFactory.getValidUser;

@SpringBootTest(classes = UserSaver.class)
class UserSaverTest {

    @Autowired
    private UserSaver userSaver;

    @MockBean
    private Users users;

    @Test()
    @DisplayName("Пользователь исправно сохраняется")
    void userCanBeSaved() {
        User validUser = getValidUser();

        userSaver.save(validUser);
        verify(users).save(validUser);
    }
}