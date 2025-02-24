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
import jakarta.ws.rs.PATCH;
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
    UserService userService;

    @GET
    @Operation(
        summary = "Retrieve all users", 
        description = "Fetches a list of all users in the system. Each user includes details such as ID, username, email, and other relevant information."
    )
    public Response getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(users).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Retrieve a user by ID", 
        description = "Fetches the details of a specific user by their unique ID. The response includes the user's username, email, and other relevant information."
    )
    public Response getUserById(@PathParam("id") String id) {
        try {
            Optional<UserDTO> userDTO = userService.getUserById(id);
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

    @GET
    @Path("/search/username/{username}")
    @Operation(
        summary = "Search users by username", 
        description = "Searches for users that match the given username. The response includes a list of users with their details, such as ID, email, and other relevant information."
    )
    public Response getUserByUsername(@PathParam("username") String username) {
        try {
            List<UserDTO> users = userService.searchUserByUsername(username);
            if (users.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(users).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Operation(
        summary = "Create a new user", 
        description = "Creates a new user in the system. The request body must include the user's username, email, and other required fields. Returns the created user with their unique ID."
    )
    public Response postUser(@Valid UserDTO userDTO) {
        try {
            UserDTO task = userService.createUser(userDTO);
            return Response.status(Response.Status.CREATED).entity(task).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete a user by ID", 
        description = "Deletes an existing user by their unique ID. If the user does not exist, a `404 Not Found` response is returned."
    )
    public Response deleteUserById(@PathParam("id") String id) {
        try {
            userService.deleteUserById(id);
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
        summary = "Update a user by ID", 
        description = "Updates specific fields of an existing user by their unique ID. The request body can include any updatable fields, such as username or email. Returns the updated user."
    )
    public Response patchUser(@PathParam("id") String id, @Valid UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return Response.status(Response.Status.OK).entity(updatedUser).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
