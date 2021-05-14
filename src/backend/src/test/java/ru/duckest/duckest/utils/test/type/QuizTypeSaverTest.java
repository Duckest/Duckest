package ru.duckest.duckest.utils.test.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.entity.QuizType;
import ru.duckest.duckest.repository.QuizTypes;
import ru.duckest.duckest.utils.test.jpa.QuizTypeTestFactory;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = QuizTypeSaver.class)
class QuizTypeSaverTest {

    @Autowired
    private QuizTypeSaver quizTypeSaver;

    @MockBean
    private QuizTypes quizTypes;

    @Test
    @DisplayName("Язык программирования можно сохранять, передав сущность")
    void levelEntityCanBeSaved() {
        QuizType dummyQuizType = QuizTypeTestFactory.getDummyQuizType();
        quizTypeSaver.save(dummyQuizType);
        verify(quizTypes).save(dummyQuizType);
    }

    @Test
    @DisplayName("Язык программирования можно сохранять, передав строку")
    void levelStringCanBeSaved() {
        QuizType dummyQuizType = QuizTypeTestFactory.getDummyQuizType();
        quizTypeSaver.save(dummyQuizType.getType());
        verify(quizTypes).save(dummyQuizType);
    }

}