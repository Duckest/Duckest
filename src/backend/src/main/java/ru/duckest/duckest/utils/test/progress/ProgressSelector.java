package ru.duckest.duckest.utils.test.progress;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.Progress;
import ru.duckest.duckest.entity.ProgressId;
import ru.duckest.duckest.repository.Progresses;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProgressSelector {

    private final Progresses progresses;

    public List<Progress> getProgressBy(UUID userId) {
        return progresses.findAllByUserId(userId);
    }

    public Progress getProgressByUserAndLevelTypePairOrReturnNew(UUID userId, UUID levelTypePair) {
        return progresses.findById(new ProgressId(userId, levelTypePair))
                .orElse(Progress.builder().userId(userId).levelTypeId(levelTypePair).build());
    }

}
