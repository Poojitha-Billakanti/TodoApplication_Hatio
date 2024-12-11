# TodoApplication_Hatio

## Description

This Todo Management Application is designed to help users manage their projects and associated tasks efficiently. The application allows users to create new projects, manage todos within each project, and export a summary of their projects as a secret gist on GitHub. The project adheres to best coding practices and uses Java, Spring Boot for the backend, and React for the frontend, with PostgreSQL as the database.

### Key Features

1. **Project Management**
   - Create a new project.
   - List all projects.
   - View project details, including editable project titles and associated todos.

2. **Todo Management**
   - Add, edit, update, and mark todos as complete or pending.
   - Todos include descriptions, status, and timestamps for creation and updates.

3. **Export to GitHub**
   - Export project summaries in markdown format as secret gists.
   - The exported gist follows a specific template for consistency.

4. **User Authentication**
   - Basic authentication for user login to ensure security.

5. **Local Storage**
   - Save exported gist files to the local system in markdown format.

### Application Architecture

- **Backend**: Java, Spring Boot
- **Frontend**: React
- **Database**: PostgreSQL
- **Version Control**: Git

## Setup Instructions

### Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd todo-management

### Backend Setup

1. **Navigate to the backend directory**
   
    ```bash
       cd backend
    ```

3. **Configure your database connection in src/main/resources/application.properties**
   
    ```bash
    server.port = <your-port>
    spring.datasource.url=spring.datasource.url=jdbc:postgresql://localhost:5432/<your-db-url>
    spring.datasource.username=<your-db-username>
    spring.datasource.password=<your-db-password>'
    ```

5. **Build the project using Maven**
   
   ```bash
   mvn clean install
    ```
   
7. **Run the Spring Boot application**
   
   ```bash
   mvn spring-boot:run
   ```

### Frontend setup

1. **Open a new terminal and navigate to the frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start the React application**
   ```bash
   npm start
   ```
   The frontend should now be running on http://localhost:3000

### Testing

  Run tests with the following commands:
    ```bash
    cd backend
    mvn test
    ```

### Project Summary Format

  When exporting a project summary, it follows this markdown format:
    ```bash
    # <Project Title>
    
    **Summary**: <No. of completed todos> / <No. of total todos> completed.
    
    ## Pending Todos
    - [ ] <Todo Description>
    
    ## Completed Todos
    - [x] <Todo Description>
    ```


