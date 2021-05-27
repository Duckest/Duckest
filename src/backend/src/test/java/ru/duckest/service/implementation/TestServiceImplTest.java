package ru.duckest.service.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.converter.QuestionConverter;
import ru.duckest.converter.TestConverter;
import ru.duckest.dto.TestCreationDto;
import ru.duckest.dto.TestDto;
import ru.duckest.dto.TypeLevelPairDto;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.entity.QuizQuestion;
import ru.duckest.service.TestService;
import ru.duckest.utils.test.TestDeleter;
import ru.duckest.utils.test.TestSaver;
import ru.duckest.utils.test.TestSelector;
import ru.duckest.utils.test.description.DescriptionSaver;
import ru.duckest.utils.test.question.QuestionSaver;
import ru.duckest.utils.test.threshold.PassThresholdSaver;
import ru.duckest.utils.test.dto.TestCreationDtoTestFactory;
import ru.duckest.utils.test.dto.TestDtoTestFactory;
import ru.duckest.utils.test.dto.TypeLevelPairTestFactory;
import ru.duckest.utils.test.jpa.QuizLevelTypePairTestFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {TestServiceImpl.class, QuestionConverter.class})
class TestServiceImplTest {

    @Autowired
    private TestService testService;
    @Autowired
    private QuestionConverter questionConverter;

    @MockBean
    private TestSelector testSelector;
    @MockBean
    private TestSaver testSaver;
    @MockBean
    private TestDeleter testDeleter;
    @MockBean
    private DescriptionSaver descriptionSaver;
    @MockBean
    private PassThresholdSaver passThresholdSaver;
    @MockBean
    private QuestionSaver questionSaver;
    @MockBean
    private TestConverter testConverter;

    @Nested
    @DisplayName("Сохранение теста")
    class SaveTest {

        @Test
        @DisplayName("Пара языка программирования и уровня находятся в бд при сохранении теста")
        void levelTypePairCanBeFoundInDatabase() {
            TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();

            testService.save(dummyTestCreationDto);
            verify(testSelector).findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
        }

        @Test
        @DisplayName("Пара языка программирования и уровня сохраняются в бд, если отсутствуют")
        void levelTypePairCanBeSaved() {
            TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();

            when(testSelector.findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType())).thenReturn(
                    Optional.empty());

            testService.save(dummyTestCreationDto);
            verify(testSaver).save(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
        }

        @Test
        @DisplayName("Описание теста сохраняется в бд")
        void descriptionCanBeSaved() {
            TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();
            QuizLevelTypePair dummyTestEntity = QuizLevelTypePairTestFactory.getDummyTestEntity();

            when(testSelector.findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType())).thenReturn(
                    Optional.of(dummyTestEntity));

            testService.save(dummyTestCreationDto);
            verify(descriptionSaver).save(dummyTestCreationDto.getDescription(), dummyTestEntity);
        }

        @Test
        @DisplayName("Проходной порог для теста сохраняется")
        void passThresholdCanBeSaved() {
            TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();
            QuizLevelTypePair dummyTestEntity = QuizLevelTypePairTestFactory.getDummyTestEntity();

            when(testSelector.findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType())).thenReturn(
                    Optional.of(dummyTestEntity));

            testService.save(dummyTestCreationDto);
            verify(passThresholdSaver).save(dummyTestCreationDto.getThreshold(), dummyTestEntity);
        }

        @Test
        @DisplayName("Вопросы и ответы для теста сохраняются")
        void questionsAndAnswersCanBeSaved() {
            TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();
            QuizLevelTypePair dummyTestEntity = QuizLevelTypePairTestFactory.getDummyTestEntity();
            List<QuizQuestion> questions = dummyTestCreationDto.getQuestions().stream().map(question -> {
                var converted = questionConverter.convert(question);
                converted.setQuizLevelTypePair(dummyTestEntity);
                return converted;
            }).collect(Collectors.toList());

            when(testSelector.findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType())).thenReturn(
                    Optional.of(dummyTestEntity));

            testService.save(dummyTestCreationDto);
            verify(questionSaver).save(questions);
        }
    }

    @Nested
    @DisplayName("Получение теста")
    class GetTest {

        @Test
        @DisplayName("Если какого-то уровня или языка программирования нет, то бросается исключение")
        void absenceOfLevelOrTypeCauseException() {
            TypeLevelPairDto dummyTypeLevelPairDto = TypeLevelPairTestFactory.getDummyTypeLevelPairDto();

            when(testSelector.findByLevelAndTypeOrThrow(dummyTypeLevelPairDto.getTestLevel(),
                                                        dummyTypeLevelPairDto.getTestType())).thenThrow(NoSuchElementException.class);

            var throwable = catchThrowable(() -> testService.getTestBy(dummyTypeLevelPairDto));
            assertThat(throwable).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("Тест исправно получен с data layer и передан в конвертацию")
        void testSuccessfullyObtainedFromDataLayer() {
            TypeLevelPairDto dummyTypeLevelPairDto = TypeLevelPairTestFactory.getDummyTypeLevelPairDto();
            QuizLevelTypePair foundToConvert = QuizLevelTypePairTestFactory.getDummyTestEntity();

            when(testSelector.findByLevelAndTypeOrThrow(dummyTypeLevelPairDto.getTestLevel(),
                                                        dummyTypeLevelPairDto.getTestType())).thenReturn(foundToConvert);

            testService.getTestBy(dummyTypeLevelPairDto);
            verify(testConverter).convert(foundToConvert);
        }

        @Test
        @DisplayName("Тест исправно получен с сервисного слоя")
        void testSuccessfullyObtainedFromServiceLayer() {
            TypeLevelPairDto dummyTypeLevelPairDto = TypeLevelPairTestFactory.getDummyTypeLevelPairDto();
            QuizLevelTypePair foundToConvert = QuizLevelTypePairTestFactory.getDummyTestEntity();
            TestDto expected = TestDtoTestFactory.getDummyTestDto();

            when(testSelector.findByLevelAndTypeOrThrow(dummyTypeLevelPairDto.getTestLevel(),
                                                        dummyTypeLevelPairDto.getTestType())).thenReturn(foundToConvert);
            when(testConverter.convert(foundToConvert)).thenReturn(TestDtoTestFactory.getDummyTestDto());

            TestDto actual = testService.getTestBy(dummyTypeLevelPairDto);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Удаление теста")
    class DeleteTest {

        @Test
        @DisplayName("Если какого-то уровня или языка программирования нет, то бросается исключение")
        void absenceOfLevelOrTypeCauseException() {
            TypeLevelPairDto dummyTypeLevelPairDto = TypeLevelPairTestFactory.getDummyTypeLevelPairDto();

            when(testSelector.findByLevelAndTypeOrThrow(dummyTypeLevelPairDto.getTestLevel(),
                                                        dummyTypeLevelPairDto.getTestType())).thenThrow(NoSuchElementException.class);

            var throwable = catchThrowable(() -> testService.delete(dummyTypeLevelPairDto));
            assertThat(throwable).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("Тест исправно получен с data layer и передан на удаление")
        void testSuccessfullyObtainedFromDataLayer() {
            TypeLevelPairDto dummyTypeLevelPairDto = TypeLevelPairTestFactory.getDummyTypeLevelPairDto();
            QuizLevelTypePair testToDelete = QuizLevelTypePairTestFactory.getDummyTestEntity();

            when(testSelector.findByLevelAndTypeOrThrow(dummyTypeLevelPairDto.getTestLevel(),
                                                        dummyTypeLevelPairDto.getTestType())).thenReturn(testToDelete);

            testService.delete(dummyTypeLevelPairDto);
            verify(testDeleter).delete(testToDelete);
        }
    }
}