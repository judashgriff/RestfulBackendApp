package com.andraskrausz.restfulapp.model;

import com.andraskrausz.restfulapp.enums.TaskStates;
import com.andraskrausz.restfulapp.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.andraskrausz.restfulapp.service.TaskService.FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {

    private final TaskService service = new TaskService();

    @Test
    void checkAndUpdateProgress() {
        TaskDTO dto = new TaskDTO();
        dto.setDateTime("2000-09-10 10:05:00");
        Task task = new Task();
        task.setDateTime(LocalDateTime.parse(dto.getDateTime(), FORMATTER));
        assertThat(TaskStates.DONE).isEqualTo(task.checkAndUpdateProgress());

        dto.setDateTime("3000-01-01 10:05:00");
        task.setDateTime(LocalDateTime.parse(dto.getDateTime(), FORMATTER));
        assertThat(TaskStates.PENDING).isEqualTo(task.checkAndUpdateProgress());
    }
}