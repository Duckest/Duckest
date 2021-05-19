package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.dto.ResultDto;
import ru.duckest.duckest.entity.Progress;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.entity.User;

import static ru.duckest.duckest.utils.test.dto.SaveResultDtoTestFactory.getDummySaveResultDto;
import static ru.duckest.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;
import static ru.duckest.duckest.utils.user.UserEntityTestFactory.getValidUser;

public class ProgressTestFactory {

    public static Progress getDummyProgress() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        User user = getValidUser();
        return Progress.builder()
                .userId(user.getId())
                .levelTypeId(dummyTestEntity.getId())
                .levelTypePair(dummyTestEntity)
                .progressValue(dummyResultDto.getResult())
                .build();
    }
}
