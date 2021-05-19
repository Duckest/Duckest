package ru.duckest.duckest.utils.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import ru.duckest.duckest.dto.TestCreationDto;
import ru.duckest.duckest.entity.QuizLevel;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.entity.QuizType;
import ru.duckest.duckest.repository.QuizLevelTypePairs;
import ru.duckest.duckest.utils.test.jpa.QuizLevelTestFactory;
import ru.duckest.duckest.utils.test.jpa.QuizTypeTestFactory;
import ru.duckest.duckest.utils.test.level.QuizLevelSaver;
import ru.duckest.duckest.utils.test.level.QuizLevelSelector;
import ru.duckest.duckest.utils.test.type.QuizTypeSaver;
import ru.duckest.duckest.utils.test.type.QuizTypeSelector;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.duckest.duckest.utils.test.dto.TestCreationDtoTestFactory.getDummyTestCreationDto;
import static ru.duckest.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;

@MockBeans({@MockBean(QuizLevelSaver.class), @MockBean(QuizTypeSaver.class)})
@SpringBootTest(classes = TestSelector.class)
class TestSelectorTest {

    @Autowired
    private TestSelector testSelector;

    @MockBean
    private QuizLevelTypePairs quizLevelTypePairs;
    @MockBean
    private QuizLevelSelector quizLevelSelector;
    @MockBean
    private QuizTypeSelector quizTypeSelector;

    @Test
    @DisplayName("Язык программирования и уровень ищутся в бд")
    void levelTypePairCanBeFoundInDatabase() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();
        QuizLevel dummyQuizLevel = QuizLevelTestFactory.getDummyQuizLevel();
        QuizType dummyQuizType = QuizTypeTestFactory.getDummyQuizType();

        when(quizLevelSelector.findBy(dummyTestCreationDto.getTestLevel())).thenReturn(Optional.of(dummyQuizLevel));
        when(quizTypeSelector.findBy(dummyTestCreationDto.getTestType())).thenReturn(Optional.of(dummyQuizType));

        testSelector.findByLevelAndType(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
        verify(quizLevelTypePairs).findByQuizLevelAndQuizType(dummyQuizLevel, dummyQuizType);
    }

    @Test
    @DisplayName("Отсутствие языка программирования провоцирует исключение")
    void levelAbsenceCauseExceptionWhenTestGetting() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();

        when(quizLevelSelector.findBy(dummyTestCreationDto.getTestLevel())).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(
                () -> testSelector.findByLevelAndTypeOrThrow(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType()));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Отсутствие типа провоцирует исключение")
    void typeAbsenceCauseExceptionWhenTestGetting() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();
        QuizLevel dummyQuizLevel = QuizLevelTestFactory.getDummyQuizLevel();

        when(quizLevelSelector.findBy(dummyTestCreationDto.getTestLevel())).thenReturn(Optional.of(dummyQuizLevel));
        when(quizTypeSelector.findBy(dummyTestCreationDto.getTestType())).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(
                () -> testSelector.findByLevelAndTypeOrThrow(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType()));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Отсутствие пары программирования и типа провоцирует исключение")
    void levelAndTypeAbsenceCauseExceptionWhenTestGetting() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();
        QuizLevel dummyQuizLevel = QuizLevelTestFactory.getDummyQuizLevel();
        QuizType dummyQuizType = QuizTypeTestFactory.getDummyQuizType();

        when(quizLevelSelector.findBy(dummyTestCreationDto.getTestLevel())).thenReturn(Optional.of(dummyQuizLevel));
        when(quizTypeSelector.findBy(dummyTestCreationDto.getTestType())).thenReturn(Optional.of(dummyQuizType));
        when(quizLevelTypePairs.findByQuizLevelAndQuizType(dummyQuizLevel, dummyQuizType)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(
                () -> testSelector.findByLevelAndTypeOrThrow(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType()));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Если всё есть в бд, то тест возвращается")
    void testReturnedIfLevelTypePairInDatabase() {
        TestCreationDto dummyTestCreationDto = getDummyTestCreationDto();
        QuizLevelTypePair expected = getDummyTestEntity();
        QuizLevel dummyQuizLevel = QuizLevelTestFactory.getDummyQuizLevel();
        QuizType dummyQuizType = QuizTypeTestFactory.getDummyQuizType();

        when(quizLevelSelector.findBy(dummyTestCreationDto.getTestLevel())).thenReturn(Optional.of(dummyQuizLevel));
        when(quizTypeSelector.findBy(dummyTestCreationDto.getTestType())).thenReturn(Optional.of(dummyQuizType));
        when(quizLevelTypePairs.findByQuizLevelAndQuizType(dummyQuizLevel, dummyQuizType)).thenReturn(Optional.of(expected));

        QuizLevelTypePair test = testSelector.findByLevelAndTypeOrThrow(dummyTestCreationDto.getTestLevel(),
                                                                        dummyTestCreationDto.getTestType());
        assertThat(test).isEqualTo(expected);
    }
}