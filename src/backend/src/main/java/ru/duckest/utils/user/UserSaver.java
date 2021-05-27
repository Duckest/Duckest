package ru.duckest.utils.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.User;
import ru.duckest.repository.Users;

@Component
@RequiredArgsConstructor
public class UserSaver {

    private final Users users;

    public void save(User user) {
        users.save(user);
    }

}
