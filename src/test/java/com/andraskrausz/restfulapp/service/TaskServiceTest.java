package com.andraskrausz.restfulapp.service;

import com.andraskrausz.restfulapp.enums.TaskStates;
import com.andraskrausz.restfulapp.model.Task;
import com.andraskrausz.restfulapp.model.TaskDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.andraskrausz.restfulapp.service.TaskService.FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private final TaskService service = new TaskService();

    @Test
    void convertToTask() {
        TaskDTO dto = new TaskDTO();
        dto.setDateTime("2000-09-10 10:05:00");
        dto.setName("Name");
        LocalDateTime time = LocalDateTime.parse(dto.getDateTime(), FORMATTER);
        Task task = service.convertToTask(dto);

        assertThat("Name").isEqualTo(task.getName());
        assertThat(time).isEqualTo(task.getDateTime());
        assertThat(task.getDescription()).isNull();
        assertThat(TaskStates.DONE).isEqualTo(task.checkAndUpdateProgress());
    }
}