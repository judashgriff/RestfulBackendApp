package com.andraskrausz.restfulapp.service;

import com.andraskrausz.restfulapp.model.Task;
import com.andraskrausz.restfulapp.model.TaskDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TaskService {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Task convertToTask(TaskDTO dto) {
        Task task = new Task();
        return convertToTask(dto, task);
    }

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
