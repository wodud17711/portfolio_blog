package com.jaeyoung.portfolio.service;

import com.jaeyoung.portfolio.domain.Project;
import com.jaeyoung.portfolio.domain.ProjectStatus;
import com.jaeyoung.portfolio.dto.ProjectDetailResponse;
import com.jaeyoung.portfolio.dto.ProjectListResponse;
import com.jaeyoung.portfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jaeyoung.portfolio.domain.Tag;
import com.jaeyoung.portfolio.domain.ProjectTag;
import com.jaeyoung.portfolio.dto.ProjectCreateRequest;
import com.jaeyoung.portfolio.dto.ProjectUpdateRequest;
import com.jaeyoung.portfolio.repository.TagRepository;
import com.jaeyoung.portfolio.repository.ProjectTagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;

    /**
     * 공개된 프로젝트 목록 조회 (페이징)
     */
    public Page<ProjectListResponse> getPublishedProjects(Pageable pageable) {
        return projectRepository.findByStatus(ProjectStatus.PUBLISHED, pageable)
                .map(ProjectListResponse::from);
    }

    /**
     * 프로젝트 상세 조회 + 조회수 증가
     */
    @Transactional
    public ProjectDetailResponse getProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다. id=" + id));

        project.increaseViewCount();
        return ProjectDetailResponse.from(project);
    }

    @Transactional
    public Long createProject(ProjectCreateRequest request) {
        Project project = Project.builder()
                .title(request.getTitle())
                .summary(request.getSummary())
                .content(request.getContent())
                .thumbnailUrl(request.getThumbnailUrl())
                .githubUrl(request.getGithubUrl())
                .demoUrl(request.getDemoUrl())
                .status(request.getStatus())
                .build();

        projectRepository.save(project);
        addTagsToProject(project, request.getTagNames());
        return project.getId();
    }

    @Transactional
    public void updateProject(Long id, ProjectUpdateRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다. id=" + id));

        project.update(
                request.getTitle(), request.getSummary(), request.getContent(),
                request.getThumbnailUrl(), request.getGithubUrl(), request.getDemoUrl(),
                request.getStatus()
        );

        // 기존 태그 즉시 DB에서 삭제 (flush로 강제)
        project.clearTags();
        projectTagRepository.deleteByProjectId(id);
        projectTagRepository.flush();   // ← DELETE를 즉시 DB에 반영

        // 그 다음 새 태그 추가
        addTagsToProject(project, request.getTagNames());
    }

    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("프로젝트를 찾을 수 없습니다. id=" + id);
        }
        projectRepository.deleteById(id);
    }

    /**
     * 어드민용 - 모든 상태(DRAFT 포함) 조회
     */
    public Page<ProjectListResponse> getAllProjectsForAdmin(Pageable pageable) {
        return projectRepository.findAll(pageable).map(ProjectListResponse::from);
    }

    /**
     * 어드민용 - 상세 (조회수 증가 X)
     */
    public ProjectDetailResponse getProjectForAdmin(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다. id=" + id));
        return ProjectDetailResponse.from(project);
    }

    private void addTagsToProject(Project project, List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) return;

        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name)
                    .orElseGet(() -> tagRepository.save(
                            Tag.builder()
                                    .name(name)
                                    .slug(toSlug(name))
                                    .build()
                    ));
            project.addProjectTag(new ProjectTag(project, tag));
        }
    }

    private String toSlug(String name) {
        return name.toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9가-힣\\-]", "");
    }
}