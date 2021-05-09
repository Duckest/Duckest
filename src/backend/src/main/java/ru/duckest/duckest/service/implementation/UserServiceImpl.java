package ru.duckest.duckest.service.implementation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import ru.duckest.duckest.converter.UserConverter;
import ru.duckest.duckest.dto.UserDto;
import ru.duckest.duckest.service.UserService;
import ru.duckest.duckest.utils.user.UserSaver;
import ru.duckest.duckest.utils.user.UserSelector;
import ru.duckest.duckest.utils.user.UserUpdater;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserSaver userSaver;
    private final UserSelector userSelector;
    private final UserUpdater userUpdater;
    private final UserConverter converter;
    private final EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public void save(UserDto user) {
        var convertedUser = converter.convert(user);
        userSaver.save(convertedUser);
    }

    @Override
    public UserDto getUserBy(String login) {
        if (!emailValidator.isValid(login)) {
            throw new IllegalArgumentException("Invalid login when getting user");
        }
        var user = userSelector.getUserByLogin(login);
        return converter.convert(user);
    }

    @Override
    public void update(UserDto user) {
        var userEntity = userSelector.getUserByLogin(user.getLogin());
        userEntity.setLogin(user.getLogin());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setMiddleName(user.getMiddleName());
        userUpdater.update(userEntity);
    }
}
