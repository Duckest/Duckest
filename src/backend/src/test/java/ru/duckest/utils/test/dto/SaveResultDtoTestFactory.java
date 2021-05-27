package ru.duckest.utils.test.dto;

import ru.duckest.dto.ResultDto;
import ru.duckest.utils.user.Constants;

import static ru.duckest.utils.test.Constants.DUMMY_TEST_LEVEL;
import static ru.duckest.utils.test.Constants.DUMMY_TEST_TYPE;

public class SaveResultDtoTestFactory {

    public static ResultDto getDummySaveResultDto() {
        return ResultDto.builder().result(80.).testType(DUMMY_TEST_TYPE).testLevel(DUMMY_TEST_LEVEL).email(Constants.VALID_EMAIL).build();
    }

    public static ResultDto getDummyResultDto() {
        return ResultDto.builder().result(80.).email(Constants.VALID_EMAIL).testType(DUMMY_TEST_TYPE).testLevel(DUMMY_TEST_LEVEL).build();
    }

}
