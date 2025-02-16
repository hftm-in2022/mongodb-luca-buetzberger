package mapper;
import entity.Task;
import entity.TaskDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskDTOConverter {
     // Convert Task entity to TaskDTO
     public TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId().toString());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setCompleted(task.isCompleted());
        return dto;
    }

    // Convert TaskDTO to Task entity
    public Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.isCompleted());
        return task;
    }
}
