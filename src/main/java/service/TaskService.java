package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import entity.Task;
import entity.TaskDTO;
import exception.DatabaseException;
import exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mapper.TaskMapper;
import repository.TaskRepository;

@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository repository;

    @Inject
    TaskMapper mapper;

    public List<TaskDTO> getAllTasks() {
        try {
            List<Task> tasks = repository.listAll();
            List<TaskDTO> taskDTOs = tasks.stream().map(mapper::toDTO).collect(Collectors.toList());
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
            Optional<Task> task = repository.findByIdOptional(objectId);
            Optional<TaskDTO> taskDTO = task.map(mapper::toDTO);
            return taskDTO;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search tasks by id");
        }
    }

    public List<TaskDTO> searchTasksByTitle(String title) {
        try {
            List<Task> tasks = repository.list("{ 'title': { $regex: ?1, $options: 'i' } }", ".*" + title + ".*");
            List<TaskDTO> taskDTOs = tasks.stream().map(mapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search tasks by title");
        }
    }

    public List<TaskDTO> searchTasksByDescription(String description) {
        try {
            List<Task> tasks = repository.list("{ 'description': { $regex: ?1, $options: 'i' } }", ".*" + description + ".*");
            List<TaskDTO> taskDTOs = tasks.stream().map(mapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search tasks by description");
        }
    }

    public List<TaskDTO> getTasksByCompletionStatus(boolean status) {
        try {
            List<Task> tasks = repository.find("completed", status).list();
            List<TaskDTO> taskDTOs = tasks.stream().map(mapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to get tasks by completion status");
        }
    }

    public List<TaskDTO> searchTasksByUsername(String username) {
        try {
            List<Task> tasks = repository.find("user.username", username).list();
            List<TaskDTO> taskDTOs = tasks.stream().map(mapper::toDTO).collect(Collectors.toList());
            return taskDTOs;
        } catch (Exception e) {
            throw new DatabaseException("Failed to search tasks by username");
        }
    }
    
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        try {
            Task task = mapper.toEntity(taskDTO);
            repository.persist(task);
            Task createdTask = repository.findById(task.getId());
            TaskDTO createdTaskDTO = mapper.toDTO(createdTask);
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
            boolean deleted = repository.deleteById(objectId);
            if (deleted == false) {
                throw new NotFoundException("Task with ID " + id + " not found in the database");
            }
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete task by id");
        }
    }
}
