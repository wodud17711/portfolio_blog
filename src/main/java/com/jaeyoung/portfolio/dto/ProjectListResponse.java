package com.jaeyoung.portfolio.dto;

import com.jaeyoung.portfolio.domain.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ProjectListResponse {

    private Long id;
    private String title;
    private String summary;
    private String thumbnailUrl;
    private List<String> tags;
    private Long viewCount;
    private LocalDateTime createdAt;

    public static ProjectListResponse from(Project project) {
        List<String> tagNames = project.getProjectTags().stream()
                .map(pt -> pt.getTag().getName())
                .toList();

        return ProjectListResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .summary(project.getSummary())
                .thumbnailUrl(project.getThumbnailUrl())
                .tags(tagNames)
                .viewCount(project.getViewCount())
                .createdAt(project.getCreatedAt())
                .build();
    }
}