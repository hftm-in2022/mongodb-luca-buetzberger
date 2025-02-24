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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.TaskService;

@Path("/task")
@Tag(name = "Task Resource", description = "Task Management API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    TaskService service;

    @GET
    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks.")
    public Response getAllTasks() {
        try {
            List<TaskDTO> tasks = service.getAllTasks();
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build(); // HTTP 204: No Content
            }
            return Response.status(Response.Status.OK).entity(tasks).build(); // HTTP 200: OK
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a task by id", description = "Returns a task by its ID.")
    public Response getTaskbyID(@PathParam("id") String id) {
        try {
            Optional<TaskDTO> taskDTO = service.getTaskById(id);
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

    // Search tasks by title
    @GET
    @Path("/search/title/{title}")
    @Operation(summary = "Search tasks by title", description = "Returns a list of tasks that match the given title.")
    public Response searchTasksByTitle(@PathParam("title") String title) {
        try {
            List<TaskDTO> tasks = service.searchTasksByTitle(title);
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build(); // HTTP 204: No Content
            }
            return Response.status(Response.Status.OK).entity(tasks).build(); // HTTP 200: OK
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500:Internal Server Error
        }
    }

    // Search tasks by description
    @GET
    @Path("/search/description/{description}")
    @Operation(summary = "Search tasks by description", description = "Returns a list of tasks that match the given description.")
    public Response searchTasksByDescription(@PathParam("description") String description) {
        try {
            List<TaskDTO> tasks = service.searchTasksByDescription(description);
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build(); // HTTP 204: No Content
            }
            return Response.status(Response.Status.OK).entity(tasks).build(); // HTTP 200: OK
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
        }
    }

    // Get tasks by completed status
    @GET
    @Path("/search/completed/{status}")
    @Operation(summary = "Get tasks by completion status", description = "Returns a list of tasks that are completed or not completed based on the given status.")
    public Response getTasksByCompletionStatus(@PathParam("status") boolean status) {
        try {
            List<TaskDTO> tasks = service.getTasksByCompletionStatus(status);
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build(); // HTTP 204: No Content
            }
            return Response.status(Response.Status.OK).entity(tasks).build(); // HTTP 200: OK
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
        }
    }

    // Search tasks by username
    @GET
    @Path("/search/user/{username}")
    @Operation(summary = "Search tasks by username", description = "Returns a list of tasks assigned to the given username.")
    public Response searchTasksByUsername(@PathParam("username") String username) {
        try {
            List<TaskDTO> tasks = service.searchTasksByUsername(username);
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build(); // HTTP 204: No Content
            }
            return Response.status(Response.Status.OK).entity(tasks).build(); // HTTP 200: OK
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
        }
    }
    
    @POST
    @Operation(summary = "Create a new task", description = "Creates a new task and returns the created task.")
    public Response createTask(@Valid TaskDTO taskDTO) {
        try {
            TaskDTO task = service.createTask(taskDTO);
            return Response.status(Response.Status.CREATED).entity(task).build(); // HTTP 201: Created
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a task by ID", description = "Deletes an existing task by its ID.")
    public Response deleteTask(@PathParam("id") String id) {
        try {
            service.deleteTaskById(id);
            return Response.status(Response.Status.NO_CONTENT).build(); // HTTP 204: No Content
        } catch (NotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build(); // HTTP 400: Bad Request
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
        }
    }

    // @PUT
    // @Path("/{id}")
    // @Operation(summary = "Update a task by ID", description = "Updates an existing task by its ID and returns the updated task.")
    // public Response updateTask(@PathParam("id") String id, @jakarta.validation.Valid TaskDTO taskDTO) {
    //     try {
    //         TaskDTO updatedTask = service.updateTask(id, taskDTO);
    //         return Response.status(Response.Status.OK).entity(updatedTask).build(); // HTTP 200: OK
    //     } catch (NotFoundException e) {
    //         return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build(); // HTTP 400: Bad Request
    //     } catch (DatabaseException e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
    //     }
    // }
}
