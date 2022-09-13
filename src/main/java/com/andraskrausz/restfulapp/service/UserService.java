package com.andraskrausz.restfulapp.service;

import com.andraskrausz.restfulapp.model.User;
import com.andraskrausz.restfulapp.model.UserDTO;
import org.springframework.stereotype.Service;

/**
 * @author András Krausz
 */

@Service
public class UserService {

    /**
     * Generates and returns a User from a UserDTO.
     *
     * @param dto Data Transfer Object containing data to generate a User object
     * @return a generated User
     */
    public User convertToUser(UserDTO dto) {
        User user = new User();
        if (null != dto.getUsername()) {
            user.setUsername(dto.getUsername());
        }
        if (null != dto.getFirstName()) {
            user.setFirstName(dto.getFirstName());
        }
        if (null != dto.getLastName()) {
            user.setLastName(dto.getLastName());
        }
        return user;
    }
}
