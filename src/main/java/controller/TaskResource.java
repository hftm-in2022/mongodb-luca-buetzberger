package controller;
import java.util.List;

import entity.TaskDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.TaskService;


@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
    
    @Inject
    TaskService service;
    
    @POST
    public Response createTask(@jakarta.validation.Valid TaskDTO taskDTO) {
        TaskDTO createdTask = service.createTask(taskDTO);
        return Response.status(201).entity(createdTask).build();
    }

    @GET
    public List<TaskDTO> getAllTasks() {
        return service.getAllTasks();
    }

    @GET
    @Path("/{id}")
    public Response getTask(@PathParam("id") String id) {
        TaskDTO task = service.getTask(id);
        return task != null ?
                Response.ok(task).build() :
                Response.status(404).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") String id) {
        service.deleteTask(id);
        return Response.noContent().build(); // HTTP 204: No Content
    }
    
    @PUT
    @Path("/{id}")
    public Response updateTask(@PathParam("id") String id, @jakarta.validation.Valid TaskDTO taskDTO) {
        TaskDTO updatedTask = service.updateTask(id, taskDTO);
        return Response.ok(updatedTask).build(); // HTTP 200: OK
    }
}
