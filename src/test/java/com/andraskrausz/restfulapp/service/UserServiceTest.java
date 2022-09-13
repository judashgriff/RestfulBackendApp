package com.andraskrausz.restfulapp.service;

import com.andraskrausz.restfulapp.enums.TaskStates;
import com.andraskrausz.restfulapp.model.Task;
import com.andraskrausz.restfulapp.model.TaskDTO;
import com.andraskrausz.restfulapp.model.User;
import com.andraskrausz.restfulapp.model.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.andraskrausz.restfulapp.service.TaskService.FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author András Krausz
 */
class UserServiceTest {

    private final UserService service = new UserService();

    @Test
    void convertToUser() {
        UserDTO dto = new UserDTO();
        dto.setUsername("JB");
        dto.setFirstName("Jim");
        User user = service.convertToUser(dto);

        assertThat(user.getUsername()).isEqualTo("JB");
        assertThat(user.getFirstName()).isEqualTo("Jim");
        assertThat(user.getLastName()).isNull();
        assertThat(user.getId()).isEqualTo(0L);
    }
}