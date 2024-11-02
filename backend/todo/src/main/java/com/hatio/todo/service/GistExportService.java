package com.hatio.todo.service;

import com.hatio.todo.entity.Project;
import com.hatio.todo.entity.Todo;
import com.hatio.todo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GistExportService {
    private final ProjectRepository projectRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${github.token}")
    public String githubToken;

    public GistExportService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void exportProjectAsGist(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        StringBuilder markdown = new StringBuilder();
        markdown.append("# ").append(project.getTitle()).append("\n");
        markdown.append("Summary: ")
                .append(project.getTodos().stream().filter(Todo::isCompleted).count())
                .append(" / ").append(project.getTodos().size()).append(" completed\n\n");

        markdown.append("## Pending Tasks\n");
        project.getTodos().stream()
                .filter(todo -> !todo.isCompleted())
                .forEach(todo -> markdown.append("- [ ] ").append(todo.getDescription()).append("\n"));

        markdown.append("\n## Completed Tasks\n");
        project.getTodos().stream()
                .filter(Todo::isCompleted)
                .forEach(todo -> markdown.append("- [x] ").append(todo.getDescription()).append("\n"));

        // Call GitHub API to create gist
        String url = "https://api.github.com/gists";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = String.format(
                "{ \"files\": { \"%s.md\": { \"content\": \"%s\" } }, \"public\": false }",
                project.getTitle(), markdown.toString().replace("\n", "\\n").replace("\"", "\\\""));

        System.out.println("Request URL: " + url);
        System.out.println("Request Headers: " + headers);
        System.out.println("Request Body: " + jsonBody);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        restTemplate.postForEntity(url, request, String.class);
    }
}
