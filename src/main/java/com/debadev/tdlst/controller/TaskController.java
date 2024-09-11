package com.debadev.tdlst.controller;

import com.debadev.tdlst.model.Task;
import com.debadev.tdlst.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    //Obtenemos todas las tareas usando el metodo findAll
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //Crear una tarea
    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskRepository.save(task);
    }

    //Obtenemos una tarea por el Id, si no se encuentra retornamos un NotFound
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            Task updatedTask = taskRepository.save(task);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{completed}")
    public List<Task> getAllCompletedTasks(@PathVariable boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

}
