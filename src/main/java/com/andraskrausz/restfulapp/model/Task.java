package com.andraskrausz.restfulapp.model;

import com.andraskrausz.restfulapp.enums.TaskStates;
import com.andraskrausz.restfulapp.service.TaskService;
import com.fasterxml.jackson.annotation.JsonAlias;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author András Krausz
 */

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq_gen")
    @SequenceGenerator(name = "tasks_seq_gen", sequenceName = "tasks_seq", allocationSize = 1)

    private long id;
    private String name;
    private String description;
    @JsonAlias("date_time")
    @Basic
    private LocalDateTime dateTime;
    private TaskStates status;
    @ManyToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TaskStates getStatus() {
        return status;
    }

    public void setStatus(TaskStates status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Checks the dateTime of the current Task and if it is in the future it sets the status to
     * TaskStates.PENDING, otherwise to TaskStates.DONE.
     *
     * @return TaskStates enum of the set state.
     */
    public TaskStates checkAndUpdateProgress() {
        if (this.dateTime.toEpochSecond(ZoneOffset.ofHours(0)) <= LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0))) {
            this.setStatus(TaskStates.DONE);
        } else {
            this.setStatus(TaskStates.PENDING);
        }
        return this.getStatus();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '"' +
                ", \"description\":\"" + description + '"' +
                ", \"date_time\":" + dateTime.format(TaskService.FORMATTER) +
                ", \"status\":\"" + status +
                "\"}";
    }
}
