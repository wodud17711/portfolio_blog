package com.jaeyoung.portfolio.dto;

import com.jaeyoung.portfolio.domain.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequest {
    private String title;
    private String summary;
    private String content;
    private String thumbnailUrl;
    private String githubUrl;
    private String demoUrl;
    private ProjectStatus status;
    private List<String> tagNames;
}