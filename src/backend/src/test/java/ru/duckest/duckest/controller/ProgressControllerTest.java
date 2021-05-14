package ru.duckest.duckest.controller;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.duckest.duckest.dto.ResultDto;
import ru.duckest.duckest.service.ProgressService;
import ru.duckest.duckest.utils.test.dto.TestTypeProgressDtoTestFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.duckest.duckest.utils.user.Constants.INVALID_EMAIL;
import static ru.duckest.duckest.utils.user.Constants.VALID_EMAIL;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProgressController.class)
class ProgressControllerTest {


    private static MockMvc controller;
    @MockBean
    private ProgressService progressService;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void initController() {
        controller = MockMvcBuilders.standaloneSetup(new ProgressController(progressService))
                .setControllerAdvice(new ControllerAdvisor())
                .build();
    }


    @Test
    @DisplayName("Сохранение прогресса происходит на сервисном слое успешно")
    void progressSavingSuccessfullyCompleteOnServiceLayer() throws Exception {
        var resultDto = ResultDto.builder().email(VALID_EMAIL).build();
        var json = mapper.writeValueAsString(resultDto);
        controller.perform(post("/progress").content(json).contentType(MediaType.APPLICATION_JSON));
        verify(progressService).saveResults(resultDto);
    }

    @Test
    @DisplayName("Сохранение прогресса происходит на сервисном слое успешно")
    void progressResultAfterSaveReturnsFromServiceLayer() throws Exception {
        var testLevel = "junior";
        var testType = "javascript";
        var resultDto = ResultDto.builder().email(VALID_EMAIL).testLevel(testLevel).testType(testType).result(25.).build();
        var json = mapper.writeValueAsString(resultDto);
        controller.perform(post("/progress").content(json).contentType(MediaType.APPLICATION_JSON));
        verify(progressService).getResult(resultDto);
    }

    @Test
    @DisplayName("Возврат результата после сохранения прогресса происходит успешно")
    void progressResultAfterSaveReturnsFromEndpoint() throws Exception {
        var testLevel = "junior";
        var testType = "javascript";
        var resultDto = ResultDto.builder().email(VALID_EMAIL).testLevel(testLevel).testType(testType).result(25.).build();
        var json = mapper.writeValueAsString(resultDto);

        when(progressService.getResult(resultDto)).thenReturn(true);

        MvcResult mvcResult = controller.perform(post("/progress").content(json).contentType(MediaType.APPLICATION_JSON)).andReturn();
        boolean testPassed = mapper.readValue(mvcResult.getResponse().getContentAsString(), boolean.class);
        assertThat(testPassed).isTrue();
    }


    @Test
    @DisplayName("Получение прогресса в тестах по невалидному email провоцирует исключение")
    void gettingTestsProgressByInvalidEmailCauseException() throws Exception {
        when(progressService.getTestsProgressBy(INVALID_EMAIL)).thenThrow(IllegalArgumentException.class);

        controller.perform(get("/progress").param("email", INVALID_EMAIL)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Получение прогресса в тестах по email c сервисного слоя проходит успешно")
    void gettingTestsProgressByEmailFromServiceLayer() throws Exception {
        controller.perform(get("/progress").param("email", VALID_EMAIL)).andExpect(status().isOk());
        verify(progressService).getTestsProgressBy(VALID_EMAIL);
    }

    @Test
    @DisplayName("Получение прогресса тестов по email проходит успешно")
    void gettingTestsProgressByEmail() throws Exception {
        var expected = TestTypeProgressDtoTestFactory.getDummyTestTypeProgressDto();
        String expectedJson = mapper.writeValueAsString(expected);

        when(progressService.getTestsProgressBy(VALID_EMAIL)).thenReturn(List.of(expected));

        MvcResult mvcResult = controller.perform(get("/progress").param("email", VALID_EMAIL)).andReturn();
        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(actual).contains(expectedJson);
    }


    @ParameterizedTest(name = "{index} Email не должен быть {0} при сохранении результата")
    @NullAndEmptySource
    @ValueSource(strings = {" ", INVALID_EMAIL})
    void emailMustNotBeNullAndBlankWhenSavingProgress(String email) throws Exception {
        var resultDto = ResultDto.builder().email(email).build();
        var json = mapper.writeValueAsString(resultDto);
        controller.perform(post("/progress").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

}