package ru.duckest.utils.test.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.duckest.utils.test.Constants.DUMMY_ANSWER_TEXT;

public class AnswerDtoTestFactory {

    private AnswerDtoTestFactory() {
    }

    public static List<String> getThreeDummyAnswers() {
        return getNDummyAnswers(3);
    }

    public static List<String> getNDummyAnswers(int n) {
        return new ArrayList<>(Collections.nCopies(n, DUMMY_ANSWER_TEXT));
    }
}
