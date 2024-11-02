package com.hatio.todo.service;

import com.hatio.todo.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GistExportServiceTest {

    @InjectMocks
    private GistExportService gistExportService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private RestTemplate restTemplate;

    private static final String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gistExportService.githubToken = GITHUB_TOKEN;
    }


    @Test
    void testExportProjectAsGist_ProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            gistExportService.exportProjectAsGist(1L);
        });

        assertEquals("Project not found", exception.getMessage());
        verify(restTemplate, never()).postForEntity(any(String.class), any(HttpEntity.class), eq(String.class));
    }
}
