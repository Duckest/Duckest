package ru.duckest.utils.test.dto;

import ru.duckest.dto.QuestionDto;
import ru.duckest.dto.TestDto;

import java.util.List;

import static ru.duckest.utils.test.Constants.DUMMY_TEST_DESCRIPTION;
import static ru.duckest.utils.test.dto.QuestionDtoTestFactory.getTwentyDummyQuestions;

public class TestDtoTestFactory {

    public static TestDto getDummyTestDto() {
        List<QuestionDto> questions = getTwentyDummyQuestions();
        return TestDto.builder().description(DUMMY_TEST_DESCRIPTION).questions(questions).totalQuestions(questions.size()).build();
    }

}
