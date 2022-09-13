package com.andraskrausz.restfulapp.controller;

import com.andraskrausz.restfulapp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testUsers() {
        Long id = create();
        getUser(id);
        getUsers(id);
        updateUser(id);
        deleteUser(id);
    }

    Long create() {
        System.out.println("Testing Create call.");
        User user = new User();
        user.setFirstName("A");
        user.setLastName("B");
        user.setUsername("AB");
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/api/user/", user, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("AB");
        assertThat(response.getBody().getFirstName()).isEqualTo("A");
        assertThat(response.getBody().getLastName()).isEqualTo("B");
        return response.getBody().getId();
    }

    void getUser(Long id) {
        System.out.println("Testing Get call.");
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/api/user/" + id, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getUsername()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("AB");
        assertThat(response.getBody().getFirstName()).isEqualTo("A");
        assertThat(response.getBody().getLastName()).isEqualTo("B");
    }

    void getUsers(Long id) {
        System.out.println("Testing Get all users call.");
        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:" + port + "/api/user", User[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isGreaterThan(0);
        Optional<User> user = Arrays.stream(response.getBody()).filter(u -> u.getId() == id).findAny();
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getUsername()).isEqualTo("AB");
        assertThat(user.get().getFirstName()).isEqualTo("A");
        assertThat(user.get().getLastName()).isEqualTo("B");
    }

    void updateUser(Long id) {
        System.out.println("Testing Update call.");
        User updated = new User();
        updated.setUsername("New");
        updated.setFirstName("First");
        updated.setLastName("Last");

        restTemplate.put("http://localhost:" + port + "/api/user/" + id, updated);
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/api/user/" + id, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getUsername()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("New");
        assertThat(response.getBody().getFirstName()).isEqualTo("First");
        assertThat(response.getBody().getLastName()).isEqualTo("Last");
    }

    void deleteUser(Long id) {
        System.out.println("Testing Delete call.");
        restTemplate.delete("http://localhost:" + port + "/api/user/" + id);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/user/" + id, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format("No user found by ID: %d.", id));
    }

    // I am not doing the Tasks testing, I think this should be enough for this test's sake.
}