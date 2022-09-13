package com.andraskrausz.restfulapp.controller;

import com.andraskrausz.restfulapp.dao.UserRepo;
import com.andraskrausz.restfulapp.model.User;
import com.andraskrausz.restfulapp.model.UserDTO;
import com.andraskrausz.restfulapp.service.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author András Krausz
 */

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
//@EnableAutoConfiguration
public class UserController {

    final UserRepo userRepo;
    final UserService service;

    public UserController(UserRepo userRepo, UserService service) {
        this.userRepo = userRepo;
        this.service = service;
    }

    /**
     * Fetches and returns all User data contained in the Database.
     * If no user is found, a message will be returned in the ResponseEntity instead.
     *
     * @return a ResponseEntity containing a String containing information about all the users.
     */
    @GetMapping("/user")
    public ResponseEntity<String> getUsers() {
        List<User> found = userRepo.findAll();
        if (found.size() > 0) {
            return new ResponseEntity<>(String.join(",", Arrays.toString(found.toArray())), HttpStatus.OK);
        }
        return new ResponseEntity<>("No known users.", HttpStatus.NOT_FOUND);
    }

    /**
     * Creates a User in the Database and returns its contents.
     *
     * @return a ResponseEntity containing a String containing information about the created user.
     */
    @PostMapping("/user")
    public ResponseEntity<String> create(@RequestBody UserDTO dto) {
        User user = service.convertToUser(dto);
        return new ResponseEntity<>(userRepo.save(user).toString(), HttpStatus.OK);
    }

    /**
     * Fetches and returns the User data contained in the Database with the ID.
     * If no user is found by the ID, a message will be returned in the ResponseEntity instead.
     *
     * @param id path variable of the User ID.
     * @return a ResponseEntity containing a String containing information about the User.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.map(value -> new ResponseEntity<>(value.toString(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(String.format("No user found by ID: %d.", id), HttpStatus.NOT_FOUND));
    }

    /**
     * Modifies the User data contained in the Database with the ID according to the request body and returns a message
     * in the ResponseEntity containing information about the operation's success.
     * If no user is found by the ID, no Database operation will take place.
     *
     * @param id  path variable of the User ID.
     * @param dto request body containing a DTO with the requested changes.
     * @return a ResponseEntity containing a String containing information about the modified User.
     */
    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        User user = service.convertToUser(dto);
        Optional<User> userFound = userRepo.findById(id);
        if (userFound.isPresent()) {
            user.setId(id);
            return new ResponseEntity<>(userRepo.save(user).toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("No user found by ID: %d.", id), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes the User contained in the Database with the ID.
     * If no user is found by the ID, a message will be returned in the ResponseEntity instead.
     *
     * @param id path variable of the User ID.
     * @return a ResponseEntity containing a String with a message about the operation's success.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> userFound = userRepo.findById(id);
        if (userFound.isPresent()) {
            userRepo.deleteById(id);
            return new ResponseEntity<>(String.format("User %d deleted", id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("No user found by ID: %d.", id), HttpStatus.NOT_FOUND);
        }
    }
}
