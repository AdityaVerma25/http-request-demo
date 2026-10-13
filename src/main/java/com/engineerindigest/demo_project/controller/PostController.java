package com.engineerindigest.demo_project.controller;

import com.engineerindigest.demo_project.model.Post;
import com.engineerindigest.demo_project.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Post createPost(@RequestParam int userId,
                           @RequestParam String title,
                           @RequestParam String content) {
        return postService.createPost(userId, title, content);
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable int userId) {
        return postService.getPostsByUser(userId);
    }
}
