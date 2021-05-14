package ru.duckest.duckest.utils.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.User;
import ru.duckest.duckest.repository.Users;

@Component
@RequiredArgsConstructor
public class UserUpdater {

    private final Users users;

    public void update(User user) {
        users.save(user);
    }
}
