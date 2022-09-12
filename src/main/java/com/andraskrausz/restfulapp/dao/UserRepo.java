package com.andraskrausz.restfulapp.dao;

import com.andraskrausz.restfulapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
