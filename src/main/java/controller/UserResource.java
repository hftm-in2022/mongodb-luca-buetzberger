package controller;

import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import entity.UserDTO;
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
import service.UserService;

@Path("/user")
@Tag(name = "User Resource", description = "User Management API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
   
    @Inject
    UserService service;

    @GET
    @Operation(summary = "Get all users", description = "Returns a list of all users.")
    public Response getAllUsers() {
        try {
            List<UserDTO> users = service.getAllUsers();
            if (users.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build(); // HTTP 204: No Content
            }
            return Response.status(Response.Status.OK).entity(users).build(); // HTTP 200: OK
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a user by id", description = "Returns a user by its ID.")
    public Response getTaskbyID(@PathParam("id") String id) {
        try {
            Optional<UserDTO> userDTO = service.getUserById(id);
            if (userDTO.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Task not found").build();
            }
            return Response.status(Response.Status.OK).entity(userDTO.get()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // Search user by username
    @GET
    @Path("/search/username/{title}")
    @Operation(summary = "Search user by username", description = "Returns a list of users that match the given username.")
    public Response searchTasksByTitle(@PathParam("title") String username) {
        try {
            List<UserDTO> users = service.searchUserByUsername(username);
            if (users.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build(); // HTTP 204: No Content
            }
            return Response.status(Response.Status.OK).entity(users).build(); // HTTP 200: OK
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500:Internal Server Error
        }
    }

    @POST
    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user.")
    public Response createTask(@Valid UserDTO userDTO) {
        try {
            UserDTO task = service.createUser(userDTO);
            return Response.status(Response.Status.CREATED).entity(task).build(); // HTTP 201: Created
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a user by ID", description = "Deletes an existing user by its ID.")
    public Response deleteTask(@PathParam("id") String id) {
        try {
            service.deleteUserById(id);
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
    // public Response updateUser(@PathParam("id") String id, @jakarta.validation.Valid UserDTO userDTO) {
    //     try {
    //         UserDTO updatedUser = service.updateUser(id, userDTO);
    //         return Response.status(Response.Status.OK).entity(updatedUser).build(); // HTTP 200: OK
    //     } catch (NotFoundException e) {
    //         return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build(); // HTTP 400: Bad Request
    //     } catch (DatabaseException e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); // HTTP 500: Internal Server Error
    //     }
    // } 
}
