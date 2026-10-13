package com.engineerindigest.demo_project.respository;

import com.engineerindigest.demo_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

    List<User> findByAgeGreaterThan(int age);
    // SELECT * FROM users WHERE age > ?

    List<User> findByNameContainingIgnoreCase(String name);
    // SELECT * FROM users WHERE LOWER(name) LIKE LOWER(CONCAT('%', ?, '%'))

    boolean existsByName(String name);
    // SELECT COUNT(*) > 0 FROM users WHERE name = ?

    List<User> findByAgeBetweenOrderByAgeAsc(int min, int max);
    // SELECT * FROM users WHERE age BETWEEN ? AND ? ORDER BY age ASC

    long countByAgeGreaterThanEqual(int age);

    List<User> findByNameContainingIgnoreCaseAndAgeGreaterThan(String name, int age);
}
