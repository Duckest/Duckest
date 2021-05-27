package ru.duckest.utils.test.jpa;

import ru.duckest.entity.Answer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.duckest.utils.test.Constants.DUMMY_ANSWER_TEXT;

public class AnswerTestFactory {

    private AnswerTestFactory() {}

    public static Answer getDummyAnswer() {
        return Answer.builder().answerText(DUMMY_ANSWER_TEXT).build();
    }

    public static List<Answer> getThreeDummyAnswers() {
        return getNDummyAnswers(3);
    }

    public static List<Answer> getNDummyAnswers(int n) {
        return Stream.generate(AnswerTestFactory::getDummyAnswer).limit(n).collect(Collectors.toList());
    }
}
