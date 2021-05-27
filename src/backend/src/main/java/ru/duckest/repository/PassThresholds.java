package ru.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.entity.PassThreshold;

import java.util.UUID;

@Repository
public interface PassThresholds extends CrudRepository<PassThreshold, UUID> {

}
