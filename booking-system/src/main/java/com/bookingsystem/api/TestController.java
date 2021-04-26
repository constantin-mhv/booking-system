package com.bookingsystem.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = { "http://localhost:3000"})
@RequestMapping("/test")

public class TestController {

    @GetMapping
    public Map getAllPeople() {
        return Collections.singletonMap("response", "Hello World");
    }
}