package service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import entity.Task;
import entity.TaskDTO;
import exception.DatabaseException;
import exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mapper.TaskDTOConverter;
import repository.TaskRepository;

@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository repository;

    @Inject
    TaskDTOConverter converter;

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        try {
            Task task = converter.toEntity(taskDTO);
            repository.persist(task);
            return converter.toDTO(task);
        } catch (Exception e) {
            throw new DatabaseException("Failed to persist Task to the database");
        }
    }

    public List<TaskDTO> getAllTasks() {
        return repository.listAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTask(String id) {
        return repository.findByIdOptional(new ObjectId(id))
                .map(converter::toDTO)
                .orElseThrow(() -> new NotFoundException("Task with ID " + id + " not found in the database"));
    }

    @Transactional
    public void deleteTask(String id) {
        Task task = repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException("Task with ID " + id + " not found in the database"));
        repository.delete(task);
    }

    @Transactional
    public TaskDTO updateTask(String id, TaskDTO taskDTO) {
        Task task = repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException("Task with ID " + id + " not found in the database"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setCompleted(taskDTO.isCompleted());

        try {
            repository.persist(task);
        } catch (Exception e) {
            throw new DatabaseException("Failed to update Task in the database");
        }

        return converter.toDTO(task);
    }
}
