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

    public User getUserBy(String email) {
        Optional<User> user = users.findUsersByEmail(email);
        return user.orElseThrow();
    }
}
