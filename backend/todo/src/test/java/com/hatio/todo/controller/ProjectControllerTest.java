package com.hatio.todo.controller;

import com.hatio.todo.entity.Project;
import com.hatio.todo.service.GistExportService;
import com.hatio.todo.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @Mock
    private GistExportService gistService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    public void testExportProjectAsGist() throws Exception {
        Long projectId = 1L;

        doNothing().when(gistService).exportProjectAsGist(projectId);

        mockMvc.perform(post("/api/projects/{id}/export-gist", projectId))
                .andExpect(status().isOk())
                .andExpect(content().string("Gist exported successfully!"));

        verify(gistService, times(1)).exportProjectAsGist(projectId);
    }

    @Test
    public void testGetAllProjects() throws Exception {
        Project project1 = new Project();
        project1.setId(1L);
        project1.setTitle("Project 1");

        Project project2 = new Project();
        project2.setId(2L);
        project2.setTitle("Project 2");

        List<Project> projects = Arrays.asList(project1, project2);
        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Project 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Project 2"));

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    public void testGetProjectById() throws Exception {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);
        project.setTitle("Project 1");

        when(projectService.getProjectById(projectId)).thenReturn(project);

        mockMvc.perform(get("/api/projects/{id}", projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(projectId))
                .andExpect(jsonPath("$.title").value("Project 1"));

        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    public void testGetProjectById_NotFound() throws Exception {
        Long projectId = 1L;

        when(projectService.getProjectById(projectId)).thenReturn(null);

        mockMvc.perform(get("/api/projects/{id}", projectId))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    public void testDeleteProject() throws Exception {
        Long projectId = 1L;

        doNothing().when(projectService).deleteProject(projectId);

        mockMvc.perform(delete("/api/projects/{id}", projectId))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteProject(projectId);
    }
}
