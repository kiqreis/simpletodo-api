package br.com.example.apirestfulsb.controllers;

import br.com.example.apirestfulsb.models.Task;
import br.com.example.apirestfulsb.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @GetMapping("/{id}")
  public ResponseEntity<Task> findById(@PathVariable(value = "id") Long id) {
    Task task = taskService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(task);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<List<Task>> findAllByUserId(@PathVariable(value = "id") Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(taskService.findByUserId(id));
  }

  @PostMapping()
  public ResponseEntity<Task> create(@Valid @RequestBody Task task) {
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(task));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> update(@Valid @RequestBody Task task, @PathVariable(value = "id") Long id) {
    task.setId(id);
    return ResponseEntity.status(HttpStatus.OK).body(taskService.update(task));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    taskService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
  }

}
