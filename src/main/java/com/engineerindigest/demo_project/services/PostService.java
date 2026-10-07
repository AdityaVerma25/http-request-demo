package com.engineerindigest.demo_project.services;

import com.engineerindigest.demo_project.exception.ResourceNotFoundException;
import com.engineerindigest.demo_project.model.Post;
import com.engineerindigest.demo_project.model.User;
import com.engineerindigest.demo_project.respository.PostRepository;
import com.engineerindigest.demo_project.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Post createPost(int userId, String title, String content){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<Post> findByUserId(int userId){
        return postRepository.findByUserId(userId);
    }
}
