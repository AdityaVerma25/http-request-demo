package com.engineerindigest.demo_project.controller;

import com.engineerindigest.demo_project.model.Post;

import com.engineerindigest.demo_project.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/{postId}")
    public Post addTagsToPost(@PathVariable int postId, @RequestBody List<String> tagNames) {
        return tagService.addTagToPosts(postId, tagNames);
    }
}
