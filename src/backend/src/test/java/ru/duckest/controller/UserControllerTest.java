package ru.duckest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.duckest.controller.ControllerAdvisor;
import ru.duckest.controller.UserController;
import ru.duckest.dto.UserDto;
import ru.duckest.service.UserService;
import ru.duckest.utils.user.UserDtoTestFactory;
import ru.duckest.utils.user.Constants;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private static MockMvc controller;
    private final UserDto validUser = UserDtoTestFactory.getValidUser();
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService userService;

    @BeforeEach
    void initController() {
        controller = MockMvcBuilders.standaloneSetup(new UserController(userService)).setControllerAdvice(new ControllerAdvisor()).build();
    }

    @ParameterizedTest(name = "{index} Email не должен быть {0} при регистрации")
    @NullAndEmptySource
    @ValueSource(strings = {" ", Constants.INVALID_EMAIL})
    void emailMustNotBeNullAndBlankWhenRegister(String email) throws Exception {
        var userWithBadEmail = UserDto.builder().email(email).build();
        var json = mapper.writeValueAsString(userWithBadEmail);
        controller.perform(post("/user").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Валидный пользователь может зарегистрироваться")
    void validUserCanBeRegister() throws Exception {
        var json = mapper.writeValueAsString(validUser);
        controller.perform(post("/user").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        verify(userService).save(validUser);
    }

    @ParameterizedTest(name = "{index} Email не должен быть {0} при обновлении")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "wrong email format"})
    void emailMustNotBeNullAndBlankWhenUpdating(String email) throws Exception {
        var userWithBadEmail = UserDto.builder().email(email).build();
        var json = mapper.writeValueAsString(userWithBadEmail);
        controller.perform(put("/user").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Несуществующий пользователь не может быть обновлён")
    void validUserCantBeUpdated() throws Exception {
        var nonExistentUser = UserDto.builder().email("validNonExistentEmail@gmail.com").build();
        var json = mapper.writeValueAsString(nonExistentUser);

        doThrow(IllegalArgumentException.class).when(userService).update(nonExistentUser);

        controller.perform(put("/user").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Существующий пользователь может быть обновлён")
    void validUserCanBeUpdated() throws Exception {
        var json = mapper.writeValueAsString(validUser);
        controller.perform(put("/user").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(userService).update(validUser);
    }

    @Test
    @DisplayName("Получение пользователя по невалидному email бросает исключение")
    void userCanBeObtainedByEmail() throws Exception {
        when(userService.getUserBy(Constants.VALID_EMAIL)).thenThrow(IllegalArgumentException.class);

        controller.perform(get("/user").param("email", Constants.VALID_EMAIL)).andExpect(status().isNotFound());
        verify(userService).getUserBy(Constants.VALID_EMAIL);
    }

    @Test
    @DisplayName("По валидному email можно получить пользователя")
    void userCanBeObtainedByValidEmail() throws Exception {
        var expected = mapper.writeValueAsString(validUser);

        when(userService.getUserBy(Constants.VALID_EMAIL)).thenReturn(validUser);

        controller.perform(get("/user").param("email", Constants.VALID_EMAIL)).andExpect(status().isOk()).andExpect(content().string(expected));
        verify(userService).getUserBy(Constants.VALID_EMAIL);
    }
}