package com.jaeyoung.portfolio.dto;

import com.jaeyoung.portfolio.domain.Project;
import com.jaeyoung.portfolio.domain.ProjectStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ProjectDetailResponse {

    private Long id;
    private String title;
    private String summary;
    private String content;
    private String thumbnailUrl;
    private String githubUrl;
    private String demoUrl;
    private List<String> tags;
    private ProjectStatus status;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjectDetailResponse from(Project project) {
        List<String> tagNames = project.getProjectTags().stream()
                .map(pt -> pt.getTag().getName())
                .toList();

        return ProjectDetailResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .summary(project.getSummary())
                .content(project.getContent())
                .thumbnailUrl(project.getThumbnailUrl())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .tags(tagNames)
                .status(project.getStatus())
                .viewCount(project.getViewCount())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}