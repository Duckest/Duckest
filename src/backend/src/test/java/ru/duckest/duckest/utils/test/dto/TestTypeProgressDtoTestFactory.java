package ru.duckest.duckest.utils.test.dto;

import ru.duckest.duckest.dto.TestTypeProgressDto;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_TYPE;
import static ru.duckest.duckest.utils.test.dto.TestLevelProgressDtosTestFactory.getDummyTestLevelProgressDtos;

public class TestTypeProgressDtoTestFactory {

    public static TestTypeProgressDto getDummyTestTypeProgressDto() {
        return TestTypeProgressDto.builder()
                .testType(DUMMY_TEST_TYPE)
                .testLevelProgressDtos(getDummyTestLevelProgressDtos())
                .levelCompleted(false)
                .build();
    }

}
