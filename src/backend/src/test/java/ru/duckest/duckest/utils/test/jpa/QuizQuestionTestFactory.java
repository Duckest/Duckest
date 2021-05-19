package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.entity.QuizQuestion;
import ru.duckest.duckest.entity.RightAnswer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_QUESTION_TEXT;

public class QuizQuestionTestFactory {

    private QuizQuestionTestFactory() {
    }

    public static QuizQuestion getDummyQuizQuestion() {
        QuizQuestion question = QuizQuestion.builder()
                .question(DUMMY_QUESTION_TEXT)
                .answers(AnswerTestFactory.getThreeDummyAnswers())
                .build();
        question.getAnswers().forEach(answer -> answer.setQuestion(question));
        question.setRightAnswer(RightAnswer.builder().rightAnswerIndex(0).question(question).build());
        return question;
    }

    public static List<QuizQuestion> getNDummyQuestions(int n) {
        return Stream.generate(QuizQuestionTestFactory::getDummyQuizQuestion).limit(n).collect(Collectors.toList());
    }

    public static List<QuizQuestion> getTwentyDummyQuestions() {
        return getNDummyQuestions(20);
    }

}
