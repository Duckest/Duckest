package ru.duckest.utils.test.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.QuizQuestion;
import ru.duckest.repository.Questions;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionSaver {

    private final Questions questions;

    public QuizQuestion save(QuizQuestion question) {
        return questions.save(question);
    }

    public List<QuizQuestion> save(List<QuizQuestion> quizQuestions) {
        return (List<QuizQuestion>) questions.saveAll(quizQuestions);
    }

}
