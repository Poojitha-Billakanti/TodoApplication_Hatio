package com.hatio.todo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    public void testSetAndGetId() {
        Todo todo = new Todo();
        Long expectedId = 1L;
        todo.setId(expectedId);
        assertEquals(expectedId, todo.getId());
    }

    @Test
    public void testSetAndGetDescription() {
        Todo todo = new Todo();
        String expectedDescription = "Test Todo";
        todo.setDescription(expectedDescription);
        assertEquals(expectedDescription, todo.getDescription());
    }

    @Test
    public void testSetAndGetCompleted() {
        Todo todo = new Todo();
        todo.setCompleted(true);
        assertEquals(true, todo.isCompleted());
    }

    @Test
    public void testSetAndGetCreatedDate() {
        Todo todo = new Todo();
        LocalDate localDate = LocalDate.of(2024, 11, 1);
        Date testDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        todo.setCreatedDate(testDate);
        assertEquals(testDate, todo.getCreatedDate());
    }

    @Test
    public void testSetAndGetUpdatedDate() {
        Todo todo = new Todo();

        LocalDate localDate = LocalDate.of(2024, 11, 1);
        Date testUpdatedDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        todo.setUpdatedDate(testUpdatedDate);
        assertEquals(testUpdatedDate, todo.getUpdatedDate());
    }

    @Test
    public void testSetAndGetProject() {
        Todo todo = new Todo();
        Project project = new Project();
        project.setId(1L);
        todo.setProject(project);
        assertEquals(project, todo.getProject());
    }
}
