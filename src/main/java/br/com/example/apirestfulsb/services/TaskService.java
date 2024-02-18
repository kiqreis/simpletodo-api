package br.com.example.apirestfulsb.services;

import br.com.example.apirestfulsb.models.Task;
import br.com.example.apirestfulsb.models.User;
import br.com.example.apirestfulsb.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserService userService;

  public Task findById(Long id) {
    Optional<Task> task = taskRepository.findById(id);
    return task.orElseThrow(() -> new ObjectNotFoundException("Task not found!"));
  }

  public List<Task> findByUserId(Long userId) {
    return taskRepository.findByUser_Id(userId);
  }

  @Transactional
  public Task create(Task task) {
    User user = userService.findById(task.getUser().getId());
    task.setId(null);
    task.setUser(user);
    return taskRepository.save(task);
  }

  @Transactional
  public Task update(Task task) {
    Task newTask = findById(task.getId());
    newTask.setDescription(task.getDescription());
    return taskRepository.save(newTask);
  }

  public void delete(Long id) {
    Task task = findById(id);
    if (task != null) {
      taskRepository.deleteById(id);
    }
  }

}
