package ru.duckest.converter;

import org.springframework.stereotype.Component;
import ru.duckest.dto.TestLevelProgressDto;
import ru.duckest.dto.TestTypeProgressDto;
import ru.duckest.entity.Progress;
import ru.duckest.entity.QuizLevelTypePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProgressConverter {

    public TestLevelProgressDto convert(Progress progress) {
        QuizLevelTypePair levelTypePair = progress.getLevelTypePair();
        return TestLevelProgressDto.builder()
                .testLevel(levelTypePair.getQuizLevel().getLevel())
                .testCompleted(progress.getProgressValue() >= levelTypePair.getPassThreshold().getThreshold())
                .progress(progress.getProgressValue())
                .build();
    }

    public Optional<TestTypeProgressDto> convertProgressWithOneType(String quizType, List<Progress> progressesWithOneType) {
        var isAllLevelsCompleted = true;
        var testTypeProgressDto = TestTypeProgressDto.builder().testType(quizType).build();
        for (Progress progress : progressesWithOneType) {
            var testLevelProgressDto = convert(progress);
            testTypeProgressDto.getTestLevelProgressDtos().add(testLevelProgressDto);
            testTypeProgressDto.setImageUrl(progress.getLevelTypePair().getQuizType().getImageUrl().getImageUrl());
            isAllLevelsCompleted &= testLevelProgressDto.isTestCompleted();
        }
        testTypeProgressDto.setLevelCompleted(isAllLevelsCompleted);
        return Optional.of(testTypeProgressDto);
    }

    public List<TestTypeProgressDto> convert(List<Progress> progresses) {
        Map<String, List<Progress>> progressesByType = new HashMap<>();
        for (Progress progress : progresses) {
            String type = progress.getLevelTypePair().getQuizType().getType();
            progressesByType.computeIfAbsent(type, typeToPut -> new ArrayList<>());
            progressesByType.get(type).add(progress);
        }
        List<TestTypeProgressDto> progress = new ArrayList<>();
        for (Map.Entry<String, List<Progress>> keyValue : progressesByType.entrySet()) {
            Optional<TestTypeProgressDto> optionalTestTypeProgressDto = convertProgressWithOneType(keyValue.getKey(), keyValue.getValue());
            optionalTestTypeProgressDto.ifPresent(progress::add);
        }
        return progress;
    }

}
