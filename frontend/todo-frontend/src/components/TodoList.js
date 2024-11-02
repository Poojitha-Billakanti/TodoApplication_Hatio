import axios from 'axios';
import React, { useEffect, useState } from 'react';
import TodoItem from './TodoItem';

const TodoList = ({ todos, projectId }) => {
    const [newTodo, setNewTodo] = useState('');
    const [updatedTodos, setUpdatedTodos] = useState(todos);

    useEffect(() => {
        setUpdatedTodos(todos); // Update todos whenever the props change
    }, [todos]);

    const addTodo = () => {
        axios.post(`http://localhost:2000/api/projects/${projectId}/todos`, { description: newTodo })
            .then(response => {
                const createdTodo = {
                    ...response.data,
                    project: { id: projectId }
                };
                setNewTodo('');
                setUpdatedTodos([...updatedTodos, createdTodo]);
            })
            .catch(error => console.error('Error adding todo:', error));
    };

    const toggleComplete = (todo) => {
        axios.put(`http://localhost:2000/api/projects/${projectId}/todos/${todo.id}`, { 
            ...todo, 
            completed: !todo.completed 
        })
        .then(response => {
            const updatedTodo = response.data;
            setUpdatedTodos(updatedTodos.map(td => (td.id === updatedTodo.id ? updatedTodo : td)));
        })
        .catch(error => console.error('Error updating todo:', error));
    };

    const pendingTodos = updatedTodos.filter(todo => !todo.completed);
    const completedTodos = updatedTodos.filter(todo => todo.completed);

    return (
        <div>
            <h3>Todos</h3>
            <input 
                type="text" 
                value={newTodo} 
                onChange={e => setNewTodo(e.target.value)} 
                placeholder="Add new todo" 
            />
            <button onClick={addTodo}>Add Todo</button>
            <h4>Pending</h4>
            <ul>
                {pendingTodos.map(todo => (
                    <TodoItem key={todo.id} todo={todo} toggleComplete={toggleComplete} />
                ))}
            </ul>
            <h4>Completed</h4>
            <ul>
                {completedTodos.map(todo => (
                    <TodoItem key={todo.id} todo={todo} toggleComplete={toggleComplete} />
                ))}
            </ul>
        </div>
    );
};

export default TodoList;
