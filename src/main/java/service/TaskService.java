package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import entity.Task;
import entity.TaskDTO;
import entity.User;
import exception.DatabaseException;
import exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mapper.TaskMapper;
import repository.TaskRepository;
import repository.UserRepository;

@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository taskRepository;
    @Inject
    UserRepository userRepository;

    @Inject
    TaskMapper taskMapper;

    public List<TaskDTO> getAllTasks() {
        try {
            List<Task> tasks = taskRepository.listAll();
            List<TaskDTO> taskDTOs = tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to get all tasks");
        }
    }

    public Optional<TaskDTO> getTaskById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new NotFoundException("Invalid Task ID format: " + id);
        }
        try {
            ObjectId objectId = new ObjectId(id);
            Optional<Task> task = taskRepository.findByIdOptional(objectId);
            Optional<TaskDTO> taskDTO = task.map(taskMapper::toDTO);
            return taskDTO;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search tasks by id");
        }
    }

    public List<TaskDTO> searchTasksByTitle(String title) {
        try {
            List<Task> tasks = taskRepository.list("{ 'title': { $regex: ?1, $options: 'i' } }", ".*" + title + ".*");
            List<TaskDTO> taskDTOs = tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search tasks by title");
        }
    }

    public List<TaskDTO> searchTasksByDescription(String description) {
        try {
            List<Task> tasks = taskRepository.list("{ 'description': { $regex: ?1, $options: 'i' } }", ".*" + description + ".*");
            List<TaskDTO> taskDTOs = tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search tasks by description");
        }
    }

    public List<TaskDTO> searchTasksByCompletionStatus(boolean status) {
        try {
            List<Task> tasks = taskRepository.find("completed", status).list();
            List<TaskDTO> taskDTOs = tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to get tasks by completion status");
        }
    }

    public List<TaskDTO> searchTasksByUsername(String username) {
        try {
            List<User> users = userRepository.list("{ 'username': ?1 }", username);
            if (users.isEmpty()) {
                throw new NotFoundException("No user found with username: " + username);
            }
            ObjectId userId = users.get(0).getId();
            List<Task> tasks = taskRepository.find("{ 'assignedUsers': ?1 }", userId).list();
            List<TaskDTO> taskDTOs = tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Failed to search tasks by username");
        }
    }
    
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        try {
            Task task = taskMapper.toEntity(taskDTO);
            taskRepository.persist(task);
            Task createdTask = taskRepository.findById(task.getId());
            TaskDTO createdTaskDTO = taskMapper.toDTO(createdTask);
            return createdTaskDTO;
        } catch (Exception e) {
            throw new DatabaseException("Failed to persist Task to the database");
        }
    }
    
    @Transactional
    public void deleteTaskById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new NotFoundException("Invalid Task ID format: " + id);
        }
        try {
            ObjectId objectId = new ObjectId(id);
            boolean deleted = taskRepository.deleteById(objectId);
            if (deleted == false) {
                throw new NotFoundException("Task with ID " + id + " not found in the database");
            }
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete task by id");
        }
    }

    @Transactional
    public TaskDTO updateTask(String id, TaskDTO taskDTO) {
        if (!ObjectId.isValid(id)) {
            throw new NotFoundException("Invalid Task ID format: " + id);
        }
        try {
            ObjectId objectId = new ObjectId(id);
            Optional<Task> existingTaskOptional = taskRepository.findByIdOptional(objectId);
            if (existingTaskOptional.isEmpty()) {
                throw new NotFoundException("Task not found with ID: " + id);
            }
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setCompleted(taskDTO.getCompleted());
            List<ObjectId> assignedUsers = taskDTO.getAssignedUsers().stream().map(ObjectId::new).collect(Collectors.toList());
            existingTask.setAssignedUsers(assignedUsers);
            taskRepository.update(existingTask);
            TaskDTO updatedTaskDTO = taskMapper.toDTO(existingTask);
            return updatedTaskDTO;
        } catch (Exception e) {
            throw new DatabaseException("Failed to partially update task with ID: " + id);
        }
    }
    
    @Transactional
    public void removeUserFromTasks(String userId) {
        if (!ObjectId.isValid(userId)) {
            throw new NotFoundException("Invalid User ID format: " + userId);
        }
        try {
            ObjectId userObjectId = new ObjectId(userId);
            List<Task> tasks = taskRepository.find("{ 'assignedUsers': ?1 }", userObjectId).list();
            for (Task task : tasks) {
                task.getAssignedUsers().remove(userObjectId);
                taskRepository.update(task);
            }
        } catch (Exception e) {
            throw new DatabaseException("Failed to remove user from tasks");
        }
    }
}
