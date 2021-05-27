package ru.duckest.utils.test.dto;

import ru.duckest.dto.TestTypeProgressDto;

import static ru.duckest.utils.test.Constants.DUMMY_TEST_TYPE;

public class TestTypeProgressDtoTestFactory {

    public static TestTypeProgressDto getDummyTestTypeProgressDto() {
        return TestTypeProgressDto.builder()
                .testType(DUMMY_TEST_TYPE)
                .testLevelProgressDtos(TestLevelProgressDtosTestFactory.getDummyTestLevelProgressDtos())
                .levelCompleted(false)
                .build();
    }

}
