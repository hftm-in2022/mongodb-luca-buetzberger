package mapper;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import entity.Task;
import entity.TaskDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskMapper {
    public TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId() != null ? task.getId().toString() : null);
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setCompleted(task.getCompleted());
        taskDTO.setAssignedUsers(
            task.getAssignedUsers() != null
                ? task.getAssignedUsers().stream().map(ObjectId::toString).collect(Collectors.toList())
                : null
        );
        return taskDTO;
    }

    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setCompleted(taskDTO.getCompleted());
        task.setAssignedUsers(
            taskDTO.getAssignedUsers() != null
                ? taskDTO.getAssignedUsers().stream().map(ObjectId::new).collect(Collectors.toList())
                : null
        );
        return task;
    }
}