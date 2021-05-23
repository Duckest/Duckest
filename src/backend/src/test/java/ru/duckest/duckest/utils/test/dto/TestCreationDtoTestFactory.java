package ru.duckest.duckest.utils.test.dto;

import ru.duckest.duckest.dto.TestCreationDto;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_IMAGE_URL;
import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_DESCRIPTION;
import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_LEVEL;
import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_TYPE;
import static ru.duckest.duckest.utils.test.dto.QuestionDtoTestFactory.getTwentyDummyQuestions;

public class TestCreationDtoTestFactory {

    private TestCreationDtoTestFactory() {
    }

    public static TestCreationDto getDummyTestCreationDto() {
        return TestCreationDto.builder()
                .testLevel(DUMMY_TEST_LEVEL)
                .testType(DUMMY_TEST_TYPE)
                .description(DUMMY_TEST_DESCRIPTION)
                .imageUrl(DUMMY_IMAGE_URL)
                .questions(getTwentyDummyQuestions())
                .build();
    }

}
