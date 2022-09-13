package com.andraskrausz.restfulapp.controller;

import com.andraskrausz.restfulapp.dao.TaskRepo;
import com.andraskrausz.restfulapp.model.Task;
import com.andraskrausz.restfulapp.model.TaskDTO;
import com.andraskrausz.restfulapp.model.User;
import com.andraskrausz.restfulapp.service.TaskService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author András Krausz
 */

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class TaskController {

    final TaskRepo taskRepo;
    final TaskService service;

    public TaskController(TaskRepo taskRepo, TaskService service) {
        this.taskRepo = taskRepo;
        this.service = service;
    }

    /**
     * Fetches all the tasks assigned to the User contained in the Database with the ID.
     * If no user is found by the ID or there are no tasks assigned to the user
     * a message will be returned in the ResponseEntity instead.
     *
     * @param userId path variable of the User ID.
     * @return a ResponseEntity containing a String containing information about the tasks assigned to the User.
     */
    @GetMapping("/user/{userId}/task")
    public ResponseEntity<String> getTasks(@PathVariable Long userId) {
        List<Task> tasks = taskRepo.findTasksByUserId(userId);
        if (tasks.size() == 0) {
            return new ResponseEntity<>(String.format("No known tasks for user by ID: %d.", userId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.join(",", Arrays.toString(tasks.toArray())), HttpStatus.OK);
    }

    /**
     * Fetches the task assigned to the User contained in the Database with the user ID that contains the received Task ID.
     * If no user is found by the user ID or there is no task assigned to the user with the received task ID
     * a message will be returned in the ResponseEntity instead.
     *
     * @param userId path variable of the User ID.
     * @param id     path variable of the Task ID.
     * @return a ResponseEntity containing a String containing information about the task assigned to the User with the received task ID.
     */
    @GetMapping("/user/{userId}/task/{id}")
    public ResponseEntity<String> getTask(@PathVariable Long userId, @PathVariable Long id) {
        Optional<Task> task = taskRepo.findTaskByUserIdAndId(userId, id);
        if (task.isEmpty()) {
            return new ResponseEntity<>("No task found by ID.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task.get().toString(), HttpStatus.OK);
    }

    /**
     * Creates a task to a User contained in the Database with the ID and returns its details in a ResponseEntity.
     * If no user is found by the ID a message will be returned in the ResponseEntity.
     *
     * @param userId path variable of the User ID.
     * @param dto    request body containing a DTO with the task details.
     * @return a ResponseEntity containing a String containing information about the task created to the User with the received ID.
     */
    @PostMapping("/user/{userId}/task")
    public ResponseEntity<String> getTasks(@PathVariable Long userId, @RequestBody TaskDTO dto) {
        Task task = service.convertToTask(dto);
        User u = new User();
        u.setId(userId);
        task.setUser(u);
        try {
            taskRepo.save(task);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(String.format("No known user with ID: %d.", userId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskRepo.save(task).toString(), HttpStatus.OK);
    }

    /**
     * Modifies a task identified by a task ID of a User contained in the Database with the user ID and returns its
     * details in a ResponseEntity.
     * If no user is found by the user ID or no task by the task ID for the User, no Database operation will take place.
     *
     * @param userId path variable of the User ID.
     * @param id     path variable of the Task ID.
     * @param dto    request body containing a DTO with the updated task details.
     * @return a ResponseEntity containing a String containing information about the task modified.
     */
    @PutMapping("/user/{userId}/task/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long userId, @PathVariable Long id, @RequestBody TaskDTO dto) {
        Optional<Task> task = taskRepo.findTaskByUserIdAndId(userId, id);
        if (task.isEmpty()) {
            return new ResponseEntity<>("No task found by ID.", HttpStatus.NOT_FOUND);
        }
        Task updated = service.convertToTask(dto, task.get());
        return new ResponseEntity<>(taskRepo.save(updated).toString(), HttpStatus.OK);
    }

    /**
     * Deletes a task of a User contained in the Database with the user ID with a matching task ID and returns a
     * confirmation message in the ResponseEntity.
     * If no user is found by the user ID or no task by the task ID, no Database operation will take place.
     *
     * @param userId path variable of the User ID.
     * @param id     path variable of the Task ID.
     * @return a ResponseEntity containing a String with a message about the operation's success.
     */
    @DeleteMapping("/user/{userId}/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long userId, @PathVariable Long id) {
        Optional<Task> task = taskRepo.findTaskByUserIdAndId(userId, id);
        if (task.isPresent()) {
            taskRepo.deleteById(id);
            return new ResponseEntity<>(String.format("Task %d deleted.", id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("No task found by ID: %d.", id), HttpStatus.NOT_FOUND);
        }
    }
}
