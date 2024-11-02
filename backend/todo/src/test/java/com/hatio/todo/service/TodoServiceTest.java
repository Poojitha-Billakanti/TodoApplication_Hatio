package com.hatio.todo.service;

import com.hatio.todo.entity.Todo;
import com.hatio.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos() {
        Long projectId = 1L;
        List<Todo> todos = new ArrayList<>();
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setDescription("Todo 1");
        todo1.setCompleted(false);
        todos.add(todo1);

        when(todoRepository.findByProjectId(projectId)).thenReturn(todos);

        List<Todo> result = todoService.getAllTodos(projectId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Todo 1", result.get(0).getDescription());
        verify(todoRepository, times(1)).findByProjectId(projectId);
    }

    @Test
    void testCreateTodo() {
        Todo todo = new Todo();
        todo.setDescription("New Todo");
        todo.setCompleted(false);

        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo createdTodo = todoService.createTodo(todo);

        assertNotNull(createdTodo);
        assertEquals("New Todo", createdTodo.getDescription());
        assertEquals(LocalDate.now(), createdTodo.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()); // compare date parts
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testUpdateTodo() {
        Long todoId = 1L;
        Todo existingTodo = new Todo();
        existingTodo.setId(todoId);
        existingTodo.setDescription("Old Description");
        existingTodo.setCompleted(false);

        Todo updatedTodoDetails = new Todo();
        updatedTodoDetails.setDescription("Updated Description");
        updatedTodoDetails.setCompleted(true);

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(existingTodo);

        Todo updatedTodo = todoService.updateTodo(todoId, updatedTodoDetails);

        assertNotNull(updatedTodo);
        assertEquals("Updated Description", updatedTodo.getDescription());
        assertTrue(updatedTodo.isCompleted());
        verify(todoRepository, times(1)).findById(todoId);
        verify(todoRepository, times(1)).save(existingTodo);
    }

    @Test
    void testDeleteTodo() {
        Long todoId = 1L;
        todoService.deleteTodo(todoId);
        verify(todoRepository, times(1)).deleteById(todoId);
    }

    @Test
    void testGetTodosByProjectId() {
        Long projectId = 1L;
        List<Todo> todos = new ArrayList<>();
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setDescription("Todo 1");
        todo1.setCompleted(false);
        todos.add(todo1);

        when(todoRepository.findByProjectId(projectId)).thenReturn(todos);

        List<Todo> result = todoService.getTodosByProjectId(projectId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Todo 1", result.get(0).getDescription());
        verify(todoRepository, times(1)).findByProjectId(projectId);
    }
}
