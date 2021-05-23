package ru.duckest.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.duckest.entity.TypeImageUrl;

import java.util.UUID;


@Repository
public interface ImageUrls extends CrudRepository<TypeImageUrl, UUID> {

}
