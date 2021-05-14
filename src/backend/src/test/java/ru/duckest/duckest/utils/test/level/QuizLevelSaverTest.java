package ru.duckest.duckest.utils.test.level;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.entity.QuizLevel;
import ru.duckest.duckest.repository.QuizLevels;
import ru.duckest.duckest.utils.test.jpa.QuizLevelTestFactory;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = QuizLevelSaver.class)
class QuizLevelSaverTest {

    @Autowired
    private QuizLevelSaver quizLevelSaver;

    @MockBean
    private QuizLevels quizLevels;

    @Test
    @DisplayName("Уровень можно сохранять, передав сущность")
    void levelEntityCanBeSaved() {
        QuizLevel dummyQuizLevel = QuizLevelTestFactory.getDummyQuizLevel();
        quizLevelSaver.save(dummyQuizLevel);
        verify(quizLevels).save(dummyQuizLevel);
    }

    @Test
    @DisplayName("Уровень можно сохранять, передав строку")
    void levelStringCanBeSaved() {
        QuizLevel dummyQuizLevel = QuizLevelTestFactory.getDummyQuizLevel();
        quizLevelSaver.save(dummyQuizLevel.getLevel());
        verify(quizLevels).save(dummyQuizLevel);
    }
}