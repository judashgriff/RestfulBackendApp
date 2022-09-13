package com.andraskrausz.restfulapp.service;


import com.andraskrausz.restfulapp.dao.TaskRepo;
import com.andraskrausz.restfulapp.model.Task;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author András Krausz
 */

@Service
public class TasksExecutor {

    private final TaskRepo taskRepo;

    public TasksExecutor(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    /**
     * Scheduled task to run every minute.
     * It fetches all Tasks from the Database and runs the checkAndUpdateProgress method on them
     * which will check the date of the task and set it to "done" if it has passed. It will return the new current state
     * whether it changed or not. If the returned state is different than the one stored in the Database, this method
     * prints a note to the console and persists the updated task in the Database.
     */
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
