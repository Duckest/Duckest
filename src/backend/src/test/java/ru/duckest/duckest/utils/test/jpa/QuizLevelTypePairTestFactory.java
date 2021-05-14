package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.entity.LevelTypeImageUrl;
import ru.duckest.duckest.entity.QuizLevelTypePair;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_IMAGE_URL;

public class QuizLevelTypePairTestFactory {

    private QuizLevelTypePairTestFactory() {
    }


    public static QuizLevelTypePair getDummyTestEntity() {
        return QuizLevelTypePair.builder()
                .quizLevel(QuizLevelTestFactory.getDummyQuizLevel())
                .quizType(QuizTypeTestFactory.getDummyQuizType())
                .passThreshold(PassThresholdTestFactory.getDummyPassThreshold())
                .imageUrl(LevelTypeImageUrl.builder().imageUrl(DUMMY_IMAGE_URL).build())
                .build();
    }
}
