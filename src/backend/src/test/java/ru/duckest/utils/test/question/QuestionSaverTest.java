package ru.duckest.utils.test.question;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.repository.Questions;
import ru.duckest.utils.test.jpa.QuizQuestionTestFactory;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = QuestionSaver.class)
class QuestionSaverTest {

    @Autowired
    private QuestionSaver questionSaver;

    @MockBean
    private Questions questions;

    @Test
    @DisplayName("Сущность вопроса исправно сохраняется в бд")
    void questionEntityCanBeSaved() {
        questionSaver.save(QuizQuestionTestFactory.getDummyQuizQuestion());
        verify(questions).save(QuizQuestionTestFactory.getDummyQuizQuestion());
    }

    @Test
    @DisplayName("Список сущностей вопросов исправно сохраняется в бд")
    void listOfQuestionEntitiesCanBeSaved() {
        questionSaver.save(QuizQuestionTestFactory.getTwentyDummyQuestions());
        verify(questions).saveAll(QuizQuestionTestFactory.getTwentyDummyQuestions());
    }

}