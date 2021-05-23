package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.entity.QuizLevelTypePair;

public class QuizLevelTypePairTestFactory {

    private QuizLevelTypePairTestFactory() {
    }


    public static QuizLevelTypePair getDummyTestEntity() {
        return QuizLevelTypePair.builder()
                .quizLevel(QuizLevelTestFactory.getDummyQuizLevel())
                .quizType(QuizTypeTestFactory.getDummyQuizType())
                .passThreshold(PassThresholdTestFactory.getDummyPassThreshold())
                .build();
    }
}
