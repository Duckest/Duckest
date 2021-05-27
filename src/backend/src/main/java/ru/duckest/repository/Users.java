package ru.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface Users extends CrudRepository<User, UUID> {

    Optional<User> findUsersByEmail(String email);
}
