package ru.duckest.utils.test.jpa;

import ru.duckest.entity.QuizType;

import static ru.duckest.utils.test.Constants.DUMMY_TEST_TYPE;
import static ru.duckest.utils.test.jpa.LevelTypeImageTestFactory.getDummyImageUrl;

public class QuizTypeTestFactory {

    private QuizTypeTestFactory() {
    }

    public static QuizType getDummyQuizType() {
        return QuizType.builder().type(DUMMY_TEST_TYPE).imageUrl(getDummyImageUrl()).build();
    }

}
