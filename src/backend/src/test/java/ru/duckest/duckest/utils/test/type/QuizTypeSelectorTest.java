package ru.duckest.duckest.utils.test.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.entity.QuizType;
import ru.duckest.duckest.repository.QuizTypes;
import ru.duckest.duckest.utils.test.jpa.QuizTypeTestFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_TYPE;

@SpringBootTest(classes = QuizTypeSelector.class)
class QuizTypeSelectorTest {

    @Autowired
    private QuizTypeSelector quizTypeSelector;

    @MockBean
    private QuizTypes quizTypes;

    @Test
    @DisplayName("Если теста для такого языка программирования нет, возвращается Optional.empty()")
    void optionalEmptyReturnedIfNoThatLevelInDatabase() {
        Optional<QuizType> testLevelOptional = quizTypeSelector.findBy(DUMMY_TEST_TYPE);

        assertThat(testLevelOptional).isEmpty();
    }

    @Test
    @DisplayName("Если теста  для такого языка программирования есть, возвращается сущность в Optional")
    void optionalWithValueReturnedIfLevelInDatabase() {
        when(quizTypes.findById(DUMMY_TEST_TYPE)).thenReturn(Optional.of(QuizTypeTestFactory.getDummyQuizType()));

        Optional<QuizType> testLevelOptional = quizTypeSelector.findBy(DUMMY_TEST_TYPE);

        assertThat(testLevelOptional).contains(QuizTypeTestFactory.getDummyQuizType());
    }

}