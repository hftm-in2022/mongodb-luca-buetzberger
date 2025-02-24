package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import entity.User;
import entity.UserDTO;
import exception.DatabaseException;
import exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mapper.UserMapper;
import repository.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @Inject
    UserMapper mapper;

    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = repository.listAll();
            List<UserDTO> userDTOs = users.stream().map(mapper::toDTO).collect(Collectors.toList());
            return userDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to get all users");
        }
    }

    public Optional<UserDTO> getUserById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new NotFoundException("Invalid User ID format: " + id);
        }
        try {
            ObjectId objectId = new ObjectId(id);
            Optional<User> user = repository.findByIdOptional(objectId);
            Optional<UserDTO> userDTO = user.map(mapper::toDTO);
            return userDTO;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search user by id");
        }
    }

    public List<UserDTO> searchUserByUsername(String username) {
        try {
            List<User> users = repository.list("{ 'username': { $regex: ?1, $options: 'i' } }", ".*" + username + ".*");
            List<UserDTO> userDTOs = users.stream().map(mapper::toDTO).collect(Collectors.toList());
            return userDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search users by username");
        }
    }
    
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        try {
            User user = mapper.toEntity(userDTO);
            repository.persist(user);
            User createdUser = repository.findById(user.getId());
            UserDTO createdUserDTO = mapper.toDTO(createdUser);
            return createdUserDTO;
        } catch (Exception e) {
            throw new DatabaseException("Failed to persist user to the database");
        }
    }
    
    @Transactional
    public void deleteUserById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new NotFoundException("Invalid user ID format: " + id);
        }
        try {
            ObjectId objectId = new ObjectId(id);
            repository.deleteById(objectId);
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete task by id");
        }
    }
}
