package com.hatio.todo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        project.setId(id);
        assertThat(project.getId()).isEqualTo(id);
    }

    @Test
    void testSetAndGetTitle() {
        String title = "Sample Project";
        project.setTitle(title);
        assertThat(project.getTitle()).isEqualTo(title);
    }

    @Test
    void testSetAndGetCreatedDate() {
        Date createdDate = new Date();
        project.setCreatedDate(createdDate);
        assertThat(project.getCreatedDate()).isEqualTo(createdDate);
    }

    @Test
    void testSetAndGetTodos() {
        Todo todo1 = new Todo();
        Todo todo2 = new Todo();
        List<Todo> todos = new ArrayList<>();
        todos.add(todo1);
        todos.add(todo2);

        project.setTodos(todos);
        assertThat(project.getTodos()).hasSize(2).contains(todo1, todo2);
    }

    @Test
    void testTodosCascadeOnProject() {
        Todo todo = new Todo();
        todo.setDescription("Test Todo");

        List<Todo> todos = new ArrayList<>();
        todos.add(todo);

        project.setTodos(todos);
        assertThat(project.getTodos().get(0).getDescription()).isEqualTo("Test Todo");

        assertThat(project.getTodos()).contains(todo);
    }
}

