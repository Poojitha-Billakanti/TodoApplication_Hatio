package com.hatio.todo.controller;

import com.hatio.todo.entity.Project;
import com.hatio.todo.entity.Todo;
import com.hatio.todo.service.ProjectService;
import com.hatio.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/projects/{projectId}/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createTodo(@PathVariable Long projectId, @RequestBody Todo todo) {
        Project project = projectService.getProjectById(projectId);

        if (project == null) {
            return ResponseEntity.badRequest().body("Project with ID " + projectId + " not found.");
        }

        todo.setProject(project);
        Todo createdTodo = todoService.createTodo(todo);

        return ResponseEntity.ok(createdTodo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Todo> getAllTodos(@PathVariable Long projectId) {
        return todoService.getAllTodos(projectId);
    }

    @GetMapping("/{id}")
    public List<Todo> getTodosByProjectId(@PathVariable Long projectId) {
        return todoService.getTodosByProjectId(projectId);
    }
}
