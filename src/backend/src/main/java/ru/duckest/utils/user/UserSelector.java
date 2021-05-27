package ru.duckest.utils.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.User;
import ru.duckest.repository.Users;

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
