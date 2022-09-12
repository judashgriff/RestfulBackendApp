package com.andraskrausz.restfulapp.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskDTOTest {

    @Test
    void setDateTime() {
        TaskDTO dto = new TaskDTO();
        String date = "2000-9-10 10:5:0";
        dto.setDateTime(date);
        assertThat("2000-09-10 10:05:00").isEqualTo(dto.getDateTime());
    }
}