package com.hatio.todo.service;

import com.hatio.todo.entity.Todo;
import com.hatio.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos(Long projectId) {
        return todoRepository.findByProjectId(projectId);
    }

    public Todo createTodo(Todo todo) {
        todo.setCreatedDate(Date.from(Instant.now()));
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            todo.setDescription(todoDetails.getDescription());
            todo.setCompleted(todoDetails.isCompleted());
            return todoRepository.save(todo);
        }
        return null;
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> getTodosByProjectId(Long projectId) {
        return todoRepository.findByProjectId(projectId);
    }
}
