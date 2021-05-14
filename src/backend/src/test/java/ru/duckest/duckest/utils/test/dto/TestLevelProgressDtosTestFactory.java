package ru.duckest.duckest.utils.test.dto;

import ru.duckest.duckest.dto.TestLevelProgressDto;

import java.util.List;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_IMAGE_URL;
import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_LEVEL;

public class TestLevelProgressDtosTestFactory {

    public static List<TestLevelProgressDto> getDummyTestLevelProgressDtos() {
        return List.of(TestLevelProgressDto.builder().testLevel(DUMMY_TEST_LEVEL).testCompleted(true).imageUrl(DUMMY_IMAGE_URL).build());
    }

}
