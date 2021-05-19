package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.entity.QuizDescription;
import ru.duckest.duckest.utils.test.dto.QuestionDtoTestFactory;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_DESCRIPTION;

public class QuizDescriptionTestFactory {

    private QuizDescriptionTestFactory() {
    }

    public static QuizDescription getDummyQuizDescription() {
        return QuizDescription.builder().description(DUMMY_TEST_DESCRIPTION).build();
    }

}
