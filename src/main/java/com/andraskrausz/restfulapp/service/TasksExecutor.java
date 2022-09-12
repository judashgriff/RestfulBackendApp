package com.andraskrausz.restfulapp.service;


import com.andraskrausz.restfulapp.dao.TaskRepo;
import com.andraskrausz.restfulapp.enums.TaskStates;
import com.andraskrausz.restfulapp.model.Task;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksExecutor {

    private final TaskRepo taskRepo;

    public TasksExecutor(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Scheduled(cron = "0 * * * * *")
    public void scheduleFixedRateTask() {
        List<Task> tasks = taskRepo.findAll();
        tasks.forEach(task -> {
            if (task.getStatus() != task.checkAndUpdateProgress()) {
                System.out.printf("Task %d for user %d, %s has been completed.%n", task.getId(), task.getUser().getId(), task.getUser().getUsername());
                taskRepo.save(task);
            }
        });
    }
}
