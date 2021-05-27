package ru.duckest.utils.test.jpa;

import ru.duckest.dto.ResultDto;
import ru.duckest.entity.Progress;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.entity.User;
import ru.duckest.utils.user.UserEntityTestFactory;

import static ru.duckest.utils.test.dto.SaveResultDtoTestFactory.getDummySaveResultDto;
import static ru.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;

public class ProgressTestFactory {

    public static Progress getDummyProgress() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        User user = UserEntityTestFactory.getValidUser();
        return Progress.builder()
                .userId(user.getId())
                .levelTypeId(dummyTestEntity.getId())
                .levelTypePair(dummyTestEntity)
                .progressValue(dummyResultDto.getResult())
                .build();
    }
}
