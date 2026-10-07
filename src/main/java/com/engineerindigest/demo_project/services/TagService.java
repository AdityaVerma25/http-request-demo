package com.engineerindigest.demo_project.services;

import com.engineerindigest.demo_project.exception.ResourceNotFoundException;
import com.engineerindigest.demo_project.model.Post;
import com.engineerindigest.demo_project.model.Tag;
import com.engineerindigest.demo_project.respository.PostRepository;
import com.engineerindigest.demo_project.respository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostRepository postRepository;

    public Post addTagToPosts(int postId, List<String> tagNames ){
        Post post = postRepository.findById(postId).orElseThrow(() -> new
                ResourceNotFoundException("Post not found"));

        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setName(tagName);
                return tagRepository.save(newTag);
            });
            post.getTags().add(tag);
        }
        return postRepository.save(post);
    }
}
