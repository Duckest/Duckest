package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.entity.QuizLevel;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_LEVEL;

public class QuizLevelTestFactory {

    private QuizLevelTestFactory() {
    }

    public static QuizLevel getDummyQuizLevel() {
        return QuizLevel.builder().level(DUMMY_TEST_LEVEL).build();
    }

}
