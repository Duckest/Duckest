package ru.duckest.duckest.utils.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.User;
import ru.duckest.duckest.repository.Users;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSelector {

    private final Users users;

    public User getUserByLogin(String login) {
        Optional<User> user = users.findUsersByLogin(login);
        return user.orElseThrow();
    }
}
