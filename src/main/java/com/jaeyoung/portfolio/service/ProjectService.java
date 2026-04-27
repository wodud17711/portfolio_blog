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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

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
}