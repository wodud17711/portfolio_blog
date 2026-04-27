package com.jaeyoung.portfolio.controller;

import com.jaeyoung.portfolio.domain.Tag;
import com.jaeyoung.portfolio.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @GetMapping
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagResponse::from)
                .toList();
    }

    public record TagResponse(Long id, String name, String slug) {
        public static TagResponse from(Tag tag) {
            return new TagResponse(tag.getId(), tag.getName(), tag.getSlug());
        }
    }
}