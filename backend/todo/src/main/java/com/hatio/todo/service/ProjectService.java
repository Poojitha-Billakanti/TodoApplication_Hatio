package com.hatio.todo.service;

import com.hatio.todo.entity.Project;
import com.hatio.todo.entity.Todo;
import com.hatio.todo.repository.ProjectRepository;
import com.hatio.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TodoRepository todoRepository;

    public String generateMarkdownSummary(Project project) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(project.getTitle()).append("\n\n");
        long completedCount = project.getTodos().stream().filter(Todo::isCompleted).count();
        sb.append("**Summary:** ").append(completedCount).append(" / ").append(project.getTodos().size()).append(" completed\n\n");

        sb.append("## Pending Tasks\n");
        project.getTodos().stream().filter(todo -> !todo.isCompleted()).forEach(todo -> {
            sb.append("- [ ] ").append(todo.getDescription()).append("\n");
        });

        sb.append("\n## Completed Tasks\n");
        project.getTodos().stream().filter(todo -> todo.isCompleted()).forEach(todo -> {
            sb.append("- [x] ").append(todo.getDescription()).append("\n");
        });

        return sb.toString();
    }

    public Project createProject(Project project) {
        project.setCreatedDate(Date.from(Instant.now()));
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.setTitle(projectDetails.getTitle());
            return projectRepository.save(project);
        }
        return null;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
