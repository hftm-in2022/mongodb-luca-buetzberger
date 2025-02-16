import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
    
    @Inject
    TaskService service;
    
    @POST
    public Response createTask(TaskDTO taskDTO) {
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
}
