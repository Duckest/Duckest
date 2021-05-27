package ru.duckest.utils.test.jpa;

import ru.duckest.entity.QuizDescription;

import static ru.duckest.utils.test.Constants.DUMMY_TEST_DESCRIPTION;

public class QuizDescriptionTestFactory {

    private QuizDescriptionTestFactory() {
    }

    public static QuizDescription getDummyQuizDescription() {
        return QuizDescription.builder().description(DUMMY_TEST_DESCRIPTION).build();
    }

}
