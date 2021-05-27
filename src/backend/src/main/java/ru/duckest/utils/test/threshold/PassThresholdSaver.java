package ru.duckest.utils.test.threshold;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.PassThreshold;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.repository.PassThresholds;

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
