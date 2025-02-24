# 🗂️ Task Management API #

This **Task Management API** is a **Quarkus-based REST API** that demonstrates database access to **MongoDB Atlas**. The API allows users to **create, retrieve, update, delete, and search tasks and users**.

## Getting Started
### 1️⃣ Prerequisites
Ensure you have the following installed:

- Java 21
- Maven
- Docker
- Swagger UI (included in the project) or tools like Postman, Bruno, or curl for testing the API.

### 2️⃣ Running the Application in Dev Mode
You can run the application in development mode with live coding enabled using:
```sh
./mvnw quarkus:dev
```

### 3️⃣ Opening Swagger UI to access the endpoints
http://localhost:8080/q/swagger-ui

### 4️⃣ Avaiable API Endpoints
**Task Endpoint**
| Method | Endpoint                                      | Description                                   |
| :----- | :-------------------------------------------- | :-------------------------------------------- |
| GET    | /task                                         | Get all tasks                                 |
| GET    | /task/{id}                                    | Get a task by its ID                          |
| GET    | /task/search/title/{title}                    | Search tasks by title                         |
| GET    | /task/search/description/{description}        | Search tasks by description                   |
| GET    | /task/search/completed/{status}               | Filter tasks by completion status             |
| GET    | /task/search/user/{username}                  | Search tasks assigned to a specific user      |
| POST   | /task                                         | Create a new task                             |
| PATCH  | /task/{id}                                    | Update a task by its ID                       |
| DELETE | /task/{id}                                    | Delete a task by its ID                       |

**User Endpoint**
| Method | Endpoint                                      | Description                                   |
| :----- | :-------------------------------------------- | :-------------------------------------------- |
| GET    | /user                                         | Get all users                                 |
| GET    | /user/{id}                                    | Get a user by their ID                        |
| GET    | /user/search/username/{username}              | Search users by username                      |
| POST   | /user                                         | Create a new user                             |
| PATCH  | /user/{id}                                    | Update a user by their ID                     |
| DELETE | /user/{id}                                    | Delete a user by their ID                     |

### 5️⃣ Example JSON for POST Endpoints
**Task**
``` json
{
  "title": "Complete Project Documentation",
  "description": "Write the README file for the project",
  "dueDate": "2025-03-01T12:00:00",
  "completed": false,
  "assignedUsers": ["64b8f3d2e4b0a3f2d4c9b123", "64b8f3d2e4b0a3f2d4c9b124"]
}
```
**User**
``` json
{
  "username": "johndoe"
}
```

### 6️⃣ Project Insight
Using MongoDB Atlas was similar to working with other databases, but I noticed some differences. For example, relationships are created by referencing ObjectIds instead of directly linking objects like in relational databases. It took a bit of time to get used to how ObjectId works differently from strings in queries, but overall, it was easy to work with and felt familiar.