package com.engineerindigest.demo_project.controller;

import com.engineerindigest.demo_project.model.Post;

import com.engineerindigest.demo_project.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping("/{postId}")
    public Post addTagsToPost(@PathVariable int postId,
                              @RequestBody List<String> tagNames) {
        return tagService.addTagsToPost(postId, tagNames);
    }
}
