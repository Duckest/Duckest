package ru.duckest.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.duckest.entity.QuizDescription;

import java.util.UUID;

@Repository
public interface Descriptions extends CrudRepository<QuizDescription, UUID> {

}
