package ru.duckest.duckest.service.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.converter.QuestionConverter;
import ru.duckest.duckest.converter.TestConverter;
import ru.duckest.duckest.dto.TestCreationDto;
import ru.duckest.duckest.dto.TestDto;
import ru.duckest.duckest.dto.TypeLevelPairDto;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.entity.QuizQuestion;
import ru.duckest.duckest.service.TestService;
import ru.duckest.duckest.utils.test.TestSaver;
import ru.duckest.duckest.utils.test.TestSelector;
import ru.duckest.duckest.utils.test.description.DescriptionSaver;
import ru.duckest.duckest.utils.test.question.QuestionSaver;
import ru.duckest.duckest.utils.test.threshold.PassThresholdSaver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.duckest.duckest.utils.test.dto.TestCreationDtoTestFactory.getDummyTestCreationDto;
import static ru.duckest.duckest.utils.test.dto.TestDtoTestFactory.getDummyTestDto;
import static ru.duckest.duckest.utils.test.dto.TypeLevelPairTestFactory.getDummyTypeLevelPairDto;
import static ru.duckest.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;


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
    private DescriptionSaver descriptionSaver;
    @MockBean
    private PassThresholdSaver passThresholdSaver;
    @MockBean
    private QuestionSaver questionSaver;
    @MockBean
    private TestConverter testConverter;


    @Test
    @DisplayName("Пара языка программирования и уровня находятся в бд при сохранении теста")
    void levelTypePairCanBeFoundInDatabase() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();

        testService.save(dummyTestCreationDto);
        verify(testSelector).findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
    }

    @Test
    @DisplayName("Пара языка программирования и уровня сохраняются в бд, если отсутствуют")
    void levelTypePairCanBeSaved() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();

        when(testSelector.findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType())).thenReturn(
                Optional.empty());

        testService.save(dummyTestCreationDto);
        verify(testSaver).save(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
    }

    @Test
    @DisplayName("Описание теста сохраняется в бд")
    void descriptionCanBeSaved() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();

        when(testSelector.findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType())).thenReturn(
                Optional.of(dummyTestEntity));

        testService.save(dummyTestCreationDto);
        verify(descriptionSaver).save(dummyTestCreationDto.getDescription(), dummyTestEntity);
    }

    @Test
    @DisplayName("Проходной порог для теста сохраняется")
    void passThresholdCanBeSaved() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();

        when(testSelector.findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType())).thenReturn(
                Optional.of(dummyTestEntity));

        testService.save(dummyTestCreationDto);
        verify(passThresholdSaver).save(dummyTestCreationDto.getThreshold(), dummyTestEntity);
    }

    @Test
    @DisplayName("Вопросы и ответы для теста сохраняются")
    void questionsAndAnswersCanBeSaved() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
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

    @Test
    @DisplayName("Если какого-то уровня или языка программирования нет, то бросается исключение")
    void absenceOfLevelOrTypeCauseExceptionWhenGettingTest() {
        TypeLevelPairDto dummyTypeLevelPairDto = getDummyTypeLevelPairDto();

        when(testSelector.findByLevelAndTypeOrThrow(dummyTypeLevelPairDto.getTestLevel(), dummyTypeLevelPairDto.getTestType())).thenThrow(
                NoSuchElementException.class);

        var throwable = catchThrowable(() -> testService.getTestBy(dummyTypeLevelPairDto));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Тест исправно получен с data layer и передан в конвертацию")
    void testSuccessfullyObtainedFromDataLayer() {
        TypeLevelPairDto dummyTypeLevelPairDto = getDummyTypeLevelPairDto();
        QuizLevelTypePair foundToConvert = getDummyTestEntity();

        when(testSelector.findByLevelAndTypeOrThrow(dummyTypeLevelPairDto.getTestLevel(), dummyTypeLevelPairDto.getTestType())).thenReturn(
                foundToConvert);

        testService.getTestBy(dummyTypeLevelPairDto);
        verify(testConverter).convert(foundToConvert);
    }

    @Test
    @DisplayName("Тест исправно получен с сервисного слоя")
    void testSuccessfullyObtainedFromServiceLayer() {
        TypeLevelPairDto dummyTypeLevelPairDto = getDummyTypeLevelPairDto();
        QuizLevelTypePair foundToConvert = getDummyTestEntity();
        TestDto expected = getDummyTestDto();

        when(testSelector.findByLevelAndTypeOrThrow(dummyTypeLevelPairDto.getTestLevel(), dummyTypeLevelPairDto.getTestType())).thenReturn(
                foundToConvert);
        when(testConverter.convert(foundToConvert)).thenReturn(getDummyTestDto());

        TestDto actual = testService.getTestBy(dummyTypeLevelPairDto);
        assertThat(actual).isEqualTo(expected);
    }
}