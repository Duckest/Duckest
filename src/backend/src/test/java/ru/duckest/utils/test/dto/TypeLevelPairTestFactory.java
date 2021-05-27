package ru.duckest.utils.test.dto;

import ru.duckest.dto.TypeLevelPairDto;

import static ru.duckest.utils.test.Constants.DUMMY_TEST_LEVEL;
import static ru.duckest.utils.test.Constants.DUMMY_TEST_TYPE;

public class TypeLevelPairTestFactory {

    public static TypeLevelPairDto getDummyTypeLevelPairDto() {
        return TypeLevelPairDto.builder().testLevel(DUMMY_TEST_LEVEL).testType(DUMMY_TEST_TYPE).build();
    }

}
