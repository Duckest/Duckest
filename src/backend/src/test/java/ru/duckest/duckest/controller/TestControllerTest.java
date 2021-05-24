package ru.duckest.duckest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.duckest.duckest.dto.TestDto;
import ru.duckest.duckest.dto.TypeLevelPairDto;
import ru.duckest.duckest.service.TestService;
import ru.duckest.duckest.utils.test.dto.TestCreationDtoTestFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TestController.class)
class TestControllerTest {

    public static final TypeLevelPairDto TYPE_LEVEL_PAIR_DTO = TypeLevelPairDto.builder()
            .testLevel("junior")
            .testType("javascript")
            .build();
    private static MockMvc controller;
    @MockBean
    private TestService testService;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void initController() {
        controller = MockMvcBuilders.standaloneSetup(new TestController(testService)).setControllerAdvice(new ControllerAdvisor()).build();
    }

    @Nested
    @DisplayName("GET /test?test_type=&test_level=")
    class GetTestByTypeAndLevel {

        @Test
        @DisplayName("Получение теста по несуществующему языку программирования и уровню c сервисного слоя провоцирует исключение")
        void gettingTestByNonExistentTypeAndLevelFromServiceLayer() throws Exception {
            when(testService.getTestBy(TYPE_LEVEL_PAIR_DTO)).thenThrow(IllegalArgumentException.class);

            controller.perform(get("/test").param("test_type", TYPE_LEVEL_PAIR_DTO.getTestType())
                                       .param("test_level", TYPE_LEVEL_PAIR_DTO.getTestLevel())).andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Получение теста по языку программирования и уровню c сервисного слоя проходит успешно")
        void gettingTestByTypeAndLevelFromServiceLayer() throws Exception {
            controller.perform(get("/test").param("test_type", TYPE_LEVEL_PAIR_DTO.getTestType())
                                       .param("test_level", TYPE_LEVEL_PAIR_DTO.getTestLevel())).andExpect(status().isOk());
            verify(testService).getTestBy(TYPE_LEVEL_PAIR_DTO);
        }

        @Test
        @DisplayName("Получение теста по языку программирования и уровню проходит успешно")
        void gettingTestByTypeAndLevel() throws Exception {
            var expected = TestDto.builder().build();

            when(testService.getTestBy(TYPE_LEVEL_PAIR_DTO)).thenReturn(expected);

            MvcResult mvcResult = controller.perform(get("/test").param("test_type", TYPE_LEVEL_PAIR_DTO.getTestType())
                                                             .param("test_level", TYPE_LEVEL_PAIR_DTO.getTestLevel()))
                    .andExpect(status().isOk())
                    .andReturn();
            var actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), TestDto.class);
            assertThat(actual).isEqualTo(expected);
        }
    }


    @Nested
    @DisplayName("POST /test")
    class CreateTest {

        @Test
        @DisplayName("Вызывает сервисный слой")
        void triggersServiceLayer() throws Exception {
            var testCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();
            var json = mapper.writeValueAsString(testCreationDto);

            controller.perform(post("/test").content(json).contentType(MediaType.APPLICATION_JSON));
            verify(testService).save(testCreationDto);
        }

        @Test
        @DisplayName("Возвращает 201 код")
        void returns201HttpCode() throws Exception {
            var testCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();
            var json = mapper.writeValueAsString(testCreationDto);

            controller.perform(post("/test").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        }

    }

    @Nested
    @DisplayName("DELETE /test")
    class DeleteTest {
        @Test
        @DisplayName("Возвращает 204 код ошибки")
        void returns204HttpCode() throws Exception {
            controller.perform(delete("/test").param("test_type", TYPE_LEVEL_PAIR_DTO.getTestType())
                                       .param("test_level", TYPE_LEVEL_PAIR_DTO.getTestLevel())).andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Вызывает сервисный слой")
        void triggersServiceLayer() throws Exception {
            controller.perform(delete("/test").param("test_type", TYPE_LEVEL_PAIR_DTO.getTestType())
                                       .param("test_level", TYPE_LEVEL_PAIR_DTO.getTestLevel()));
            verify(testService).delete(TYPE_LEVEL_PAIR_DTO);
        }
    }
}