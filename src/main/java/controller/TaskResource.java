package controller;

import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import entity.TaskDTO;
import exception.DatabaseException;
import exception.NotFoundException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.TaskService;
import service.UserService;

@Path("/task")
@Tag(name = "Task Resource", description = "Task Management API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    TaskService taskService;
    @Inject
    UserService userService;

    @GET
    @Operation(
        summary = "Retrieve all tasks", 
        description = "Fetches a list of all tasks in the system. Each task includes details such as ID, title, description, due date, completion status, and assigned users."
    )
    public Response getAllTasks() {
        try {
            List<TaskDTO> tasks = taskService.getAllTasks();
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(tasks).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Retrieve a task by ID", 
        description = "Fetches the details of a specific task by its unique ID. The response includes the task's title, description, due date, completion status, and assigned users."
    )
    public Response getTaskByID(@PathParam("id") String id) {
        try {
            Optional<TaskDTO> taskDTO = taskService.getTaskById(id);
            if (taskDTO.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Task not found").build();
            }
            return Response.status(Response.Status.OK).entity(taskDTO.get()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/title/{title}")
    @Operation(
        summary = "Search tasks by title", 
        description = "Searches for tasks that match the given title. The response includes a list of tasks with their details, such as ID, description, due date, completion status, and assigned users."
    )
    public Response getTasksByTitle(@PathParam("title") String title) {
        try {
            List<TaskDTO> tasks = taskService.searchTasksByTitle(title);
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(tasks).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/description/{description}")
    @Operation(
        summary = "Search tasks by description", 
        description = "Searches for tasks that match the given description. The response includes a list of tasks with their details, such as ID, title, due date, completion status, and assigned users."
    )
    public Response getTasksByDescription(@PathParam("description") String description) {
        try {
            List<TaskDTO> tasks = taskService.searchTasksByDescription(description);
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(tasks).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/completed/{status}")
    @Operation(
        summary = "Filter tasks by completion status", 
        description = "Fetches a list of tasks filtered by their completion status. Pass `true` to retrieve completed tasks or `false` for incomplete tasks. The response includes task details such as ID, title, description, due date, and assigned users."
    )
    public Response getTasksByCompletionStatus(@PathParam("status") boolean status) {
        try {
            List<TaskDTO> tasks = taskService.searchTasksByCompletionStatus(status);
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(tasks).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/user/{username}")
    @Operation(
        summary = "Search tasks by username", 
        description = "Fetches a list of tasks assigned to a specific user. The response includes task details such as ID, title, description, due date, completion status, and assigned users."
    )
    public Response getTasksByUsername(@PathParam("username") String username) {
        try {
            List<TaskDTO> tasks = taskService.searchTasksByUsername(username);
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(tasks).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Operation(
        summary = "Create a new task", 
        description = "Creates a new task in the system. The request body must include the task's title, description, due date, completion status, and assigned users. Returns the created task with its unique ID."
    )
    public Response postTask(@Valid TaskDTO taskDTO) {
        try {
            for (String userId : taskDTO.getAssignedUsers()) {
                if (!userService.userExists(userId)) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("User with ID " + userId + " does not exist.").build();
                }
            }
            TaskDTO task = taskService.createTask(taskDTO);
            return Response.status(Response.Status.CREATED).entity(task).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete a task by ID", 
        description = "Deletes an existing task by its unique ID. If the task does not exist, a `404 Not Found` response is returned."
    )
    public Response deleteTask(@PathParam("id") String id) {
        try {
            taskService.deleteTaskById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Operation(
        summary = "Update a task by ID", 
        description = "Updates specific fields of an existing task by its unique ID. The request body can include any updatable fields, such as title, description, due date, completion status, or assigned users. Returns the updated task."
    )
    public Response patchTask(@PathParam("id") String id, TaskDTO taskDTO) {
        try {
            for (String userId : taskDTO.getAssignedUsers()) {
                if (!userService.userExists(userId)) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("User with ID " + userId + " does not exist.").build();
                }
            }
            TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
            return Response.status(Response.Status.OK).entity(updatedTask).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
