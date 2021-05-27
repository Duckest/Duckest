package ru.duckest.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.duckest.dto.UserDto;
import ru.duckest.entity.User;
import ru.duckest.utils.user.UserDtoTestFactory;
import ru.duckest.utils.user.UserEntityTestFactory;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = UserConverter.class)
class UserConverterTest {

    private final UserDto validUser = UserDtoTestFactory.getValidUser();
    private final User validUserEntity = UserEntityTestFactory.getValidUser();

    @Autowired
    private UserConverter converter;

    @Test
    @DisplayName("DTO исправно конвертируется в сущность")
    void dtoCanBeConvertedToEntity() {
        validUserEntity.setId(null);

        User actual = converter.convert(validUser);
        assertThat(actual).isEqualTo(validUserEntity);
    }


    @Test
    @DisplayName("Сущность исправно конвертируется в dto")
    void entityCanBeConvertedToDto() {
        UserDto actual = converter.convert(validUserEntity);
        assertThat(actual).isEqualTo(validUser);
    }

}