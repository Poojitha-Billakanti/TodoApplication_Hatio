import axios from 'axios';
import React, { useState } from 'react';
import TodoList from './TodoList';

const Project = ({ project }) => {
    const [todos, setTodos] = useState([]);
    const [message, setMessage] = useState('');

    // Fetch todos for the project
    const fetchTodos = () => {
        axios.get(`http://localhost:2000/api/projects/${project.id}/todos`)
            .then(response => {
                const todosWithProject = response.data.map(todo => ({
                    ...todo,
                    project: { id: project.id }
                }));
                setTodos(todosWithProject);
            })
            .catch(error => console.error('Error fetching todos:', error));
    };

    // Function to export project summary as a GitHub gist
    const exportAsGist = () => {
        axios.post(`http://localhost:2000/api/projects/${project.id}/export-gist`)
            .then(() => setMessage('Gist exported successfully!'))
            .catch(error => {
                console.error('Error exporting gist:', error);
                setMessage('Failed to export gist');
            });
    };

    // Function to download the project summary as a Markdown file
    const downloadMarkdown = () => {
        const completedTodos = todos.filter(todo => todo.completed);
        const pendingTodos = todos.filter(todo => !todo.completed);

        const markdownContent = `
# ${project.title}

Summary: ${completedTodos.length} / ${todos.length} completed

## Pending Tasks
${pendingTodos.map(todo => `- [ ] ${todo.description}`).join('\n')}

## Completed Tasks
${completedTodos.map(todo => `- [x] ${todo.description}`).join('\n')}
        `;

        const blob = new Blob([markdownContent], { type: 'text/markdown' });
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `${project.title}.md`;
        link.click();
        URL.revokeObjectURL(url);
    };

    return (
        <li>
            <h2>{project.title}</h2>
            <button onClick={fetchTodos}>View Todos</button>
            <button onClick={exportAsGist}>Export as Gist</button>
            <button onClick={downloadMarkdown}>Download Markdown</button>
            <TodoList todos={todos} projectId={project.id} />
            {message && <p>{message}</p>}
        </li>
    );
};

export default Project;
