package com.hatio.todo.controller;

import com.hatio.todo.entity.Project;
import com.hatio.todo.entity.Todo;
import com.hatio.todo.service.ProjectService;
import com.hatio.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @MockBean
    private ProjectService projectService;

    private Project mockProject;
    private Todo mockTodo;

    @BeforeEach
    public void setUp() {
        mockProject = new Project();
        mockProject.setId(1L);
        mockProject.setTitle("Sample Project");

        mockTodo = new Todo();
        mockTodo.setId(1L);
        mockTodo.setDescription("Sample Todo");
        mockTodo.setCompleted(false);
        mockTodo.setProject(mockProject);
    }


    @Test
    public void testDeleteTodo() throws Exception {
        mockMvc.perform(delete("/api/projects/1/todos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllTodos() throws Exception {
        List<Todo> todos = Arrays.asList(mockTodo);
        when(todoService.getAllTodos(1L)).thenReturn(todos);

        mockMvc.perform(get("/api/projects/1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("Sample Todo"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    public void testGetTodosByProjectId() throws Exception {
        List<Todo> todos = Arrays.asList(mockTodo);
        when(todoService.getTodosByProjectId(1L)).thenReturn(todos);

        mockMvc.perform(get("/api/projects/1/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("Sample Todo"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }
}
