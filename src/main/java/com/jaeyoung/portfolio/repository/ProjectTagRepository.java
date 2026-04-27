package com.jaeyoung.portfolio.repository;

import com.jaeyoung.portfolio.domain.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {

    List<ProjectTag> findByProjectId(Long projectId);

    List<ProjectTag> findByTagId(Long tagId);

    void deleteByProjectId(Long projectId);
}