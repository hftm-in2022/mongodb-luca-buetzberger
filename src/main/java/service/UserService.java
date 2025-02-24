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
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Inject
    TaskService taskService;

    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.listAll();
            List<UserDTO> userDTOs = users.stream().map(userMapper::toDTO).collect(Collectors.toList());
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
            Optional<User> user = userRepository.findByIdOptional(objectId);
            Optional<UserDTO> userDTO = user.map(userMapper::toDTO);
            return userDTO;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search user by id");
        }
    }

    public List<UserDTO> searchUserByUsername(String username) {
        try {
            List<User> users = userRepository.list("{ 'username': { $regex: ?1, $options: 'i' } }", ".*" + username + ".*");
            List<UserDTO> userDTOs = users.stream().map(userMapper::toDTO).collect(Collectors.toList());
            return userDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search users by username");
        }
    }
    
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            userRepository.persist(user);
            User createdUser = userRepository.findById(user.getId());
            UserDTO createdUserDTO = userMapper.toDTO(createdUser);
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
            taskService.removeUserFromTasks(id);
            userRepository.deleteById(objectId);
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete task by id");
        }
    }

    
    @Transactional
    public UserDTO updateUser(String id, UserDTO userDTO) {
        if (!ObjectId.isValid(id)) {
            throw new NotFoundException("Invalid User ID format: " + id);
        }
        try {
            ObjectId objectId = new ObjectId(id);
            Optional<User> existingUserOptional = userRepository.findByIdOptional(objectId);
            if (existingUserOptional.isEmpty()) {
                throw new NotFoundException("User not found with ID: " + id);
            }
            User existingUser = existingUserOptional.get();
            existingUser.setUsername(userDTO.getUsername());
            userRepository.update(existingUser);
            UserDTO updatedUser = userMapper.toDTO(existingUser);
            return updatedUser;
        } catch (Exception e) {
            throw new DatabaseException("Failed to update user with ID: " + id);
        }
    }

    public boolean userExists(String id) {
        try {
            return getUserById(id).isPresent();
        } catch (NotFoundException e) {
            return false;
        } catch (DatabaseException e) {
            throw new DatabaseException("Error checking user existence: " + e.getMessage());
        }
    }
}
