package service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import repository.TaskRepository;
import entity.Task;
import entity.TaskDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mapper.TaskDTOConverter;

@ApplicationScoped
public class TaskService {
 
    @Inject
    TaskRepository repository;

    @Inject
    TaskDTOConverter converter;

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = converter.toEntity(taskDTO);
        repository.persist(task);
        return converter.toDTO(task);
    }

    public List<TaskDTO> getAllTasks() {
        return repository.listAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTask(String id) {
        Task task = repository.findByIdOptional(new ObjectId(id)).orElse(null);
        return task != null ? converter.toDTO(task) : null;
    }
}
