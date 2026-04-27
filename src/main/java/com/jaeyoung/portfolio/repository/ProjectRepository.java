package com.jaeyoung.portfolio.repository;

import com.jaeyoung.portfolio.domain.Project;
import com.jaeyoung.portfolio.domain.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 상태별 페이징 조회 (예: PUBLISHED만)
    Page<Project> findByStatus(ProjectStatus status, Pageable pageable);

    // 제목 검색 (부분 일치)
    Page<Project> findByTitleContaining(String keyword, Pageable pageable);

    // 상태 + 제목 검색 결합
    Page<Project> findByStatusAndTitleContaining(
            ProjectStatus status, String keyword, Pageable pageable
    );
}