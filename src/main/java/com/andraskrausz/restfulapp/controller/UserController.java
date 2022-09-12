package com.andraskrausz.restfulapp.controller;

import com.andraskrausz.restfulapp.dao.TaskRepo;
import com.andraskrausz.restfulapp.dao.UserRepo;
import com.andraskrausz.restfulapp.model.Task;
import com.andraskrausz.restfulapp.model.TaskDTO;
import com.andraskrausz.restfulapp.model.User;
import com.andraskrausz.restfulapp.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
public class UserController {

    final UserRepo userRepo;
    final TaskRepo taskRepo;
    final TaskService service;
    final ObjectMapper mapper = new ObjectMapper();

    public UserController(UserRepo userRepo, TaskRepo taskRepo, TaskService service) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> getUsers() {
        List<User> found = userRepo.findAll();
        if (found.size() > 0) {
            return new ResponseEntity<>(String.join(",", Arrays.toString(found.toArray())), HttpStatus.OK);
        }
        return new ResponseEntity<>("No known users.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody User user) {
        return new ResponseEntity<>(userRepo.save(user).toString(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.map(value -> new ResponseEntity<>(value.toString(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(String.format("No user found by ID: %d.", id), HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userFound = userRepo.findById(id);
        if (userFound.isPresent()) {
            user.setId(id);
            return new ResponseEntity<>(userRepo.save(user).toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("No user found by ID: %d.", id), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> userFound = userRepo.findById(id);
        if (userFound.isPresent()) {
            userRepo.deleteById(id);
            return new ResponseEntity<>(String.format("User %d deleted", id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("No user found by ID: %d.", id), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/task")
    public ResponseEntity<String> getTasks(@PathVariable Long userId) {
        List<Task> tasks = taskRepo.findTasksByUserId(userId);
        if (tasks.size() == 0) {
            return new ResponseEntity<>(String.format("No known tasks for user by ID: %d.", userId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.join(",", Arrays.toString(tasks.toArray())), HttpStatus.OK);
    }

    @GetMapping("/{userId}/task/{id}")
    public ResponseEntity<String> getTask(@PathVariable Long userId, @PathVariable Long id) {
        Optional<Task> task = taskRepo.findTaskByUserIdAndId(userId, id);
        if (task.isEmpty()) {
            return new ResponseEntity<>("No task found by ID.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task.get().toString(), HttpStatus.OK);
    }

    @PostMapping("/{userId}/task")
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

    @PutMapping("/{userId}/task/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long userId, @PathVariable Long id, @RequestBody TaskDTO dto) {
        Optional<Task> task = taskRepo.findTaskByUserIdAndId(userId, id);
        if (task.isEmpty()) {
            return new ResponseEntity<>("No task found by ID.", HttpStatus.NOT_FOUND);
        }
        Task updated = service.convertToTask(dto, task.get());
        return new ResponseEntity<>(taskRepo.save(updated).toString(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/task/{id}")
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
