package com.engineerindigest.demo_project.services;

import com.engineerindigest.demo_project.exception.ResourceNotFoundException;
import com.engineerindigest.demo_project.model.Post;
import com.engineerindigest.demo_project.model.User;
import com.engineerindigest.demo_project.respository.PostRepository;
import com.engineerindigest.demo_project.respository.UserRepository;
import com.engineerindigest.demo_project.security.AccessControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AccessControl accessControl;

    public Post createPost(int userId, String title, String content) {
        accessControl.allowSameUserOrAdmin(userId);
        log.info("Creating post for userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        return postRepository.save(post);
    }

    public List<Post> getPostsByUser(int userId) {
        accessControl.allowSameUserOrAdmin(userId);
        log.info("Fetching posts for userId: {}", userId);
        return postRepository.findByUserId(userId);
    }
}