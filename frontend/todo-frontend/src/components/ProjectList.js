import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Project from './Project';

const ProjectList = () => {
    const [projects, setProjects] = useState([]);
    const [newProjectTitle, setNewProjectTitle] = useState('');

    useEffect(() => {
        axios.get('http://localhost:2000/api/projects')
            .then(response => setProjects(response.data))
            .catch(error => console.error('Error fetching projects:', error));
    }, []);

    const createProject = () => {
        if (newProjectTitle) {
            axios.post('http://localhost:2000/api/projects', { title: newProjectTitle })
                .then(response => {
                    setProjects([...projects, response.data]);
                    setNewProjectTitle(''); // Clear the input
                })
                .catch(error => console.error('Error creating project:', error));
        }
    };

    return (
        <div>
            <h1>Projects</h1>
            <input
                type="text"
                value={newProjectTitle}
                onChange={(e) => setNewProjectTitle(e.target.value)}
                placeholder="Enter project title"
            />
            <button onClick={createProject}>Add Project</button>
            <ul>
                {projects.map(project => (
                    <Project key={project.id} project={project} />
                ))}
            </ul>
        </div>
    );
};

export default ProjectList;
