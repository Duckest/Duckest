package ru.duckest.utils.test.level;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.entity.QuizLevel;
import ru.duckest.repository.QuizLevels;
import ru.duckest.utils.test.jpa.QuizLevelTestFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.duckest.utils.test.Constants.DUMMY_TEST_LEVEL;

@SpringBootTest(classes = QuizLevelSelector.class)
class QuizLevelSelectorTest {

    @Autowired
    private QuizLevelSelector quizLevelSelector;

    @MockBean
    private QuizLevels quizLevels;

    @Test
    @DisplayName("Если теста с уровнем нет, возвращается Optional.empty()")
    void optionalEmptyReturnedIfNoThatLevelInDatabase() {
        Optional<QuizLevel> testLevelOptional = quizLevelSelector.findBy(DUMMY_TEST_LEVEL);

        assertThat(testLevelOptional).isEmpty();
    }

    @Test
    @DisplayName("Если теста с уровнем есть, возвращается сущность в Optional")
    void optionalWithValueReturnedIfLevelInDatabase() {
        when(quizLevels.findById(DUMMY_TEST_LEVEL)).thenReturn(Optional.of(QuizLevelTestFactory.getDummyQuizLevel()));

        Optional<QuizLevel> testLevelOptional = quizLevelSelector.findBy(DUMMY_TEST_LEVEL);

        assertThat(testLevelOptional).contains(QuizLevelTestFactory.getDummyQuizLevel());
    }

}