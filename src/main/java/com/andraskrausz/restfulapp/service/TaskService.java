package com.andraskrausz.restfulapp.service;

import com.andraskrausz.restfulapp.model.Task;
import com.andraskrausz.restfulapp.model.TaskDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author András Krausz
 */

@Service
public class TaskService {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Generates and returns a Task from a TaskDTO.
     *
     * @param dto Data Transfer Object containing data to generate a Task object
     * @return a generated Task
     */
    public Task convertToTask(TaskDTO dto) {
        Task task = new Task();
        return convertToTask(dto, task);
    }

    /**
     * Sets the received Task according to the received TaskDTO and returns.
     *
     * @param dto  Data Transfer Object containing data to generate a Task object
     * @param task Task to set and returned
     * @return a generated Task
     */
    public Task convertToTask(TaskDTO dto, Task task) {
        if (null != dto.getDateTime()) {
            task.setDateTime(LocalDateTime.parse(dto.getDateTime(), FORMATTER));
            task.checkAndUpdateProgress();
        }
        if (null != dto.getName()) {
            task.setName(dto.getName());
        }
        if (null != dto.getDescription()) {
            task.setDescription(dto.getDescription());
        }
        return task;
    }
}
