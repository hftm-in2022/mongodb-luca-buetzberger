
import java.util.List;

import org.bson.types.ObjectId;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TaskService {
 
    @Inject
    TaskRepository repository;

    @Transactional
    public Task createTask(Task task) {
        repository.persist(task);
        return task;
    }

    public List<Task> getAllTasks() {
        return repository.listAll();
    }

    public Task getTask(String id) {
        if (!ObjectId.isValid(id)) return null;
        return repository.findById(new ObjectId(id));
    }
}
