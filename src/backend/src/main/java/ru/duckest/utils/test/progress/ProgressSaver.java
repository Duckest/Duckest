package ru.duckest.utils.test.progress;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.Progress;
import ru.duckest.repository.Progresses;

@Component
@RequiredArgsConstructor
public class ProgressSaver {

    private final Progresses progresses;

    public Progress save(Progress progress) {
        return progresses.save(progress);
    }
}
