package com.engineerindigest.demo_project.respository;

import com.engineerindigest.demo_project.model.RefreshToken;
import com.engineerindigest.demo_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    void deleteByUser(User user);

    Optional<RefreshToken> findByToken(String token);

}
