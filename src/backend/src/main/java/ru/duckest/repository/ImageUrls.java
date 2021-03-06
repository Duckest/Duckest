package ru.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.entity.TypeImageUrl;

import java.util.UUID;


@Repository
public interface ImageUrls extends CrudRepository<TypeImageUrl, UUID> {

}
