package ru.duckest.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.duckest.dto.ResultDto;
import ru.duckest.dto.TestTypeProgressDto;
import ru.duckest.converter.ProgressConverter;
import ru.duckest.entity.Progress;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.service.ProgressService;
import ru.duckest.utils.passdate.QuizPassDateSaver;
import ru.duckest.utils.test.TestSelector;
import ru.duckest.utils.test.progress.ProgressSaver;
import ru.duckest.utils.test.progress.ProgressSelector;
import ru.duckest.utils.user.UserSelector;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final TestSelector testSelector;
    private final UserSelector userSelector;
    private final ProgressSaver progressSaver;
    private final ProgressSelector progressSelector;
    private final ProgressConverter progressConverter;
    private final QuizPassDateSaver quizPassDateSaver;

    private boolean isResultPassedThreshold(Double result, Integer threshold) {
        return result - Double.valueOf(threshold) >= -1e-5;
    }

    @Override
    public void saveResults(ResultDto resultDto) {
        var levelTypePair = testSelector.findByLevelAndTypeOrThrow(resultDto.getTestLevel(), resultDto.getTestType());
        var levelTypePairId = levelTypePair.getId();
        var userId = userSelector.getUserBy(resultDto.getEmail()).getId();
        var oldResult = progressSelector.getProgressByUserAndLevelTypePairOrReturnNew(userId, levelTypePairId);
        if (oldResult.getProgressValue() < resultDto.getResult()) {
            var newProgress = Progress.builder().userId(userId).levelTypeId(levelTypePairId).progressValue(resultDto.getResult()).build();
            progressSaver.save(newProgress);
            if (isResultPassedThreshold(resultDto.getResult(), levelTypePair.getPassThreshold().getThreshold())) {
                quizPassDateSaver.save(userId, levelTypePairId);
            }
        }
    }

    @Override
    public List<TestTypeProgressDto> getTestsProgressBy(String email) {
        var user = userSelector.getUserBy(email);
        List<Progress> userProgress = progressSelector.getProgressBy(user.getId());
        var allTests = testSelector.findAllTests();
        for (Progress progress : userProgress) {
            allTests.removeIf(test -> test.equals(progress.getLevelTypePair()));
        }
        for (QuizLevelTypePair test : allTests) {
            userProgress.add(Progress.builder().levelTypePair(test).build());
        }
        return progressConverter.convert(userProgress);
    }

    @Override
    public boolean getResult(ResultDto resultDto) {
        var levelTypePair = testSelector.findByLevelAndTypeOrThrow(resultDto.getTestLevel(), resultDto.getTestType());
        return isResultPassedThreshold(resultDto.getResult(), levelTypePair.getPassThreshold().getThreshold());
    }
}
