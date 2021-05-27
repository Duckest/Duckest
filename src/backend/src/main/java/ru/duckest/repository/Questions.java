package ru.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.entity.QuizQuestion;

import java.util.UUID;

@Repository
public interface Questions extends CrudRepository<QuizQuestion, UUID> {

}
