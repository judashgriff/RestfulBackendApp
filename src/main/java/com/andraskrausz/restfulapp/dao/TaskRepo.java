package com.andraskrausz.restfulapp.dao;

import com.andraskrausz.restfulapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author András Krausz
 */


public interface TaskRepo extends JpaRepository<Task, Long> {

    /**
     * Fetches and returns all the task from the Database associated to the user with the received user ID.
     *
     * @param id the user id.
     * @return a list of tasks that are found
     */
    List<Task> findTasksByUserId(Long id);

    /**
     * Fetches and returns the task from the Database associated to the user with the received user ID that has the task ID.
     *
     * @param userId the user id.
     * @param id     the task id.
     * @return an optional containing a task that is found
     */
    Optional<Task> findTaskByUserIdAndId(Long userId, Long id);
}
