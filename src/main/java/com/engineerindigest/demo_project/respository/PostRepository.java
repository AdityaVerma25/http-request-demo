package com.engineerindigest.demo_project.respository;

import com.engineerindigest.demo_project.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(int userId);
}
