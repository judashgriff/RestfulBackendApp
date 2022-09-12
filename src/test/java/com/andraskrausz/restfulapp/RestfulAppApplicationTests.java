package com.andraskrausz.restfulapp;

import com.andraskrausz.restfulapp.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
class RestfulAppApplicationTests {

    @Autowired
    private UserController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
