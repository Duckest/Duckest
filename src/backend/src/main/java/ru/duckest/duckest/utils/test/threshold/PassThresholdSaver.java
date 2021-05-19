package ru.duckest.duckest.utils.test.threshold;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.PassThreshold;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.repository.PassThresholds;

@Component
@RequiredArgsConstructor
public class PassThresholdSaver {

    private final PassThresholds passThresholds;

    public PassThreshold save(PassThreshold threshold) {
        return passThresholds.save(threshold);
    }

    public PassThreshold save(Integer threshold, QuizLevelTypePair levelTypePair) {
        return save(PassThreshold.builder().levelTypeId(levelTypePair.getId()).threshold(threshold).build());
    }

}
