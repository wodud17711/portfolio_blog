package com.jaeyoung.portfolio.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 500)
    private String summary;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @Column(name = "demo_url", length = 500)
    private String demoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status = ProjectStatus.DRAFT;

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @Builder
    public Project(String title, String summary, String content,
                   String thumbnailUrl, String githubUrl, String demoUrl,
                   ProjectStatus status) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.githubUrl = githubUrl;
        this.demoUrl = demoUrl;
        this.status = status != null ? status : ProjectStatus.DRAFT;
    }

    // 비즈니스 메서드
    public void increaseViewCount() {
        this.viewCount++;
    }

    public void update(String title, String summary, String content,
                       String thumbnailUrl, String githubUrl, String demoUrl,
                       ProjectStatus status) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.githubUrl = githubUrl;
        this.demoUrl = demoUrl;
        this.status = status;
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTag> projectTags = new ArrayList<>();
}