package ru.skypro.lessons.springboot.webLibrary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByUsername(String name);
}
