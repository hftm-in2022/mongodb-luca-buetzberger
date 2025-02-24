package mapper;
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
        // if (task.getUser() != null) {
        //     UserDTO userDTO = new UserDTO();
        //     userDTO.setUsername(task.getUser().getUsername());
        //     userDTO.setEmail(task.getUser().getEmail());
        //     taskDTO.setUser(userDTO);
        // }
        return taskDTO;
    }

    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setCompleted(taskDTO.isCompleted());
        // if (taskDTO.getUser() != null) {
        //     User user = new User();
        //     user.setUsername(taskDTO.getUser().getUsername());
        //     user.setEmail(taskDTO.getUser().getEmail());
        //     task.setUser(user);
        // }
        return task;
    }
}