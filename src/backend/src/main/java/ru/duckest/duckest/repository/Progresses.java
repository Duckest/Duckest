package ru.duckest.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.duckest.entity.Progress;
import ru.duckest.duckest.entity.ProgressId;

import java.util.List;
import java.util.UUID;

@Repository
public interface Progresses extends CrudRepository<Progress, ProgressId> {

    List<Progress> findAllByUserId(UUID userId);

}
