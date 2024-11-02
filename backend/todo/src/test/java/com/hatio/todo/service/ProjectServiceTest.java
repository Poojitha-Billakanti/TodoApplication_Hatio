package com.hatio.todo.service;

import com.hatio.todo.entity.Project;
import com.hatio.todo.entity.Todo;
import com.hatio.todo.repository.ProjectRepository;
import com.hatio.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllProjects() {
        Project project1 = new Project();
        project1.setTitle("Project 1");
        Project project2 = new Project();
        project2.setTitle("Project 2");

        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = projectService.getAllProjects();

        assertNotNull(projects);
        assertEquals(2, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProjectById() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);
        project.setTitle("Project 1");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Project foundProject = projectService.getProjectById(projectId);

        assertNotNull(foundProject);
        assertEquals(projectId, foundProject.getId());
        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    void testUpdateProject() {
        Long projectId = 1L;
        Project existingProject = new Project();
        existingProject.setId(projectId);
        existingProject.setTitle("Old Project");

        Project updatedDetails = new Project();
        updatedDetails.setTitle("Updated Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any(Project.class))).thenReturn(existingProject);

        Project updatedProject = projectService.updateProject(projectId, updatedDetails);

        assertNotNull(updatedProject);
        assertEquals("Updated Project", updatedProject.getTitle());
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(existingProject);
    }

    @Test
    void testDeleteProject() {
        Long projectId = 1L;
        doNothing().when(projectRepository).deleteById(projectId);

        projectService.deleteProject(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void testGenerateMarkdownSummary() {
        Project project = new Project();
        project.setTitle("Sample Project");

        Todo todo1 = new Todo();
        todo1.setDescription("Task 1");
        todo1.setCompleted(true);
        Todo todo2 = new Todo();
        todo2.setDescription("Task 2");
        todo2.setCompleted(false);

        project.setTodos(Arrays.asList(todo1, todo2));

        String summary = projectService.generateMarkdownSummary(project);

        String expectedSummary = "# Sample Project\n\n" +
                "**Summary:** 1 / 2 completed\n\n" +
                "## Pending Tasks\n" +
                "- [ ] Task 2\n\n" +
                "## Completed Tasks\n" +
                "- [x] Task 1\n";

        assertEquals(expectedSummary, summary);
    }
}
