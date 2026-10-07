package com.engineerindigest.demo_project.controller;

import com.engineerindigest.demo_project.model.Post;
import com.engineerindigest.demo_project.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @Operation(summary = " Create a post")
    public Post createPost(@RequestParam int userId,
                           @RequestParam String title,
                           @RequestParam String content) {
        return postService.createPost(userId, title, content);
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostByUser(@PathVariable int userId) {
        return postService.findByUserId(userId);
    }
}
