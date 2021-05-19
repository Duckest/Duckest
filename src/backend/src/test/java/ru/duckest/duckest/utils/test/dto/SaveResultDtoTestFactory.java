package ru.duckest.duckest.utils.test.dto;

import ru.duckest.duckest.dto.ResultDto;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_LEVEL;
import static ru.duckest.duckest.utils.test.Constants.DUMMY_TEST_TYPE;
import static ru.duckest.duckest.utils.user.Constants.VALID_EMAIL;

public class SaveResultDtoTestFactory {

    public static ResultDto getDummySaveResultDto() {
        return ResultDto.builder().result(80.).testType(DUMMY_TEST_TYPE).testLevel(DUMMY_TEST_LEVEL).email(VALID_EMAIL).build();
    }

    public static ResultDto getDummyResultDto() {
        return ResultDto.builder().result(80.).email(VALID_EMAIL).testType(DUMMY_TEST_TYPE).testLevel(DUMMY_TEST_LEVEL).build();
    }

}
