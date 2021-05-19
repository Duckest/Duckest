package ru.duckest.duckest.utils.test.dto;

import ru.duckest.duckest.dto.QuestionDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_QUESTION_TEXT;
import static ru.duckest.duckest.utils.test.dto.AnswerDtoTestFactory.getThreeDummyAnswers;

public class QuestionDtoTestFactory {

    private QuestionDtoTestFactory() {

    }

    public static QuestionDto getQuestionWithThreeAnswersAndFirstRight() {
        return QuestionDto.builder().question(DUMMY_QUESTION_TEXT).answers(getThreeDummyAnswers()).rightAnswerIndex(0).build();
    }

    public static List<QuestionDto> getNDummyQuestions(int n) {
        return Stream.generate(QuestionDtoTestFactory::getQuestionWithThreeAnswersAndFirstRight).limit(n).collect(Collectors.toList());
    }

    public static List<QuestionDto> getTwentyDummyQuestions() {
        return getNDummyQuestions(20);
    }

}
