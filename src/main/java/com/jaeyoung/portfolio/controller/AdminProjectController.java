package com.jaeyoung.portfolio.controller;

import com.jaeyoung.portfolio.dto.ProjectCreateRequest;
import com.jaeyoung.portfolio.dto.ProjectDetailResponse;
import com.jaeyoung.portfolio.dto.ProjectListResponse;
import com.jaeyoung.portfolio.dto.ProjectUpdateRequest;
import com.jaeyoung.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {

    private final ProjectService projectService;

    @GetMapping
    public Page<ProjectListResponse> getAllProjects(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return projectService.getAllProjectsForAdmin(pageable);
    }

    @GetMapping("/{id}")
    public ProjectDetailResponse getProject(@PathVariable Long id) {
        return projectService.getProjectForAdmin(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createProject(@RequestBody ProjectCreateRequest request) {
        Long id = projectService.createProject(request);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectUpdateRequest request
    ) {
        projectService.updateProject(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}