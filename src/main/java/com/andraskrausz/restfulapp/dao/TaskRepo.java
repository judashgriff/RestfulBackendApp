package com.andraskrausz.restfulapp.dao;

import com.andraskrausz.restfulapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findTasksByUserId(Long id);

    Optional<Task> findTaskByUserIdAndId(Long userId, Long id);
}
