package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.entity.QuizType;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_TYPE;
import static ru.duckest.duckest.utils.test.jpa.LevelTypeImageTestFactory.getDummyImageUrl;

public class QuizTypeTestFactory {

    private QuizTypeTestFactory() {
    }

    public static QuizType getDummyQuizType() {
        return QuizType.builder().type(DUMMY_TEST_TYPE).imageUrl(getDummyImageUrl()).build();
    }

}
