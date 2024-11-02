import React from 'react';

const TodoItem = ({ todo, toggleComplete }) => {
    return (
        <li>
            <span style={{ textDecoration: todo.completed ? 'line-through' : 'none' }}>
                {todo.description}
            </span>
            <button onClick={() => toggleComplete(todo)}>
                {todo.completed ? 'Undo' : 'Complete'}
            </button>
        </li>
    );
};

export default TodoItem;
