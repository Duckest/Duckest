package ru.duckest.utils.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.dto.TestCreationDto;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.repository.QuizLevelTypePairs;
import ru.duckest.utils.test.dto.TestCreationDtoTestFactory;
import ru.duckest.utils.test.level.QuizLevelSaver;
import ru.duckest.utils.test.level.QuizLevelSelector;
import ru.duckest.utils.test.type.QuizTypeSaver;
import ru.duckest.utils.test.type.QuizTypeSelector;
import ru.duckest.utils.test.jpa.QuizLevelTypePairTestFactory;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestSaver.class)
class TestSaverTest {

    @Autowired
    private TestSaver testSaver;

    @MockBean
    private QuizLevelTypePairs quizLevelTypePairs;
    @MockBean
    private QuizLevelSaver quizLevelSaver;
    @MockBean
    private QuizLevelSelector quizLevelSelector;
    @MockBean
    private QuizTypeSaver quizTypeSaver;
    @MockBean
    private QuizTypeSelector quizTypeSelector;

    @Test
    @DisplayName("quiz_level из Dto ищется в бд")
    void quizLevelCanBeObtainedFromDatabase() {
        TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();

        testSaver.save(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
        verify(quizLevelSelector).findBy(dummyTestCreationDto.getTestLevel());
    }

    @Test
    @DisplayName("quiz_level из Dto не ищется в бд, но сохраняется")
    void quizLevelCantBeObtainedFromDatabaseAndSaving() {
        TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();

        when(quizLevelSelector.findBy(dummyTestCreationDto.getTestLevel())).thenReturn(Optional.empty());

        testSaver.save(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
        verify(quizLevelSaver).save(dummyTestCreationDto.getTestLevel());
    }

    @Test
    @DisplayName("quiz_type из Dto ищется в бд")
    void quizTypeCanBeObtainedFromDatabase() {
        TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();

        testSaver.save(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
        verify(quizTypeSelector).findBy(dummyTestCreationDto.getTestType());
    }

    @Test
    @DisplayName("quiz_type из Dto не ищется в бд, но сохраняется")
    void quizTypeCantBeObtainedFromDatabaseAndSaving() {
        TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();

        when(quizTypeSelector.findBy(dummyTestCreationDto.getTestType())).thenReturn(Optional.empty());

        testSaver.save(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
        verify(quizTypeSaver).save(dummyTestCreationDto.getTestType());
    }


    @Test
    @DisplayName("Пара языка программирования и уровня теста успешно сохраняется")
    void levelTypePairCanBeSaved() {
        TestCreationDto dummyTestCreationDto = TestCreationDtoTestFactory.getDummyTestCreationDto();
        QuizLevelTypePair dummyTestEntity = QuizLevelTypePairTestFactory.getDummyTestEntity();
        dummyTestEntity.setPassThreshold(null);

        when(quizLevelSelector.findBy(dummyTestCreationDto.getTestLevel())).thenReturn(Optional.of(dummyTestEntity.getQuizLevel()));
        when(quizTypeSelector.findBy(dummyTestCreationDto.getTestType())).thenReturn(Optional.of(dummyTestEntity.getQuizType()));

        testSaver.save(dummyTestCreationDto.getTestLevel(), dummyTestCreationDto.getTestType());
        verify(quizLevelTypePairs).save(dummyTestEntity);
    }

}