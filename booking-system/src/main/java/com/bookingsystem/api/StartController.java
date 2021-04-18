package com.bookingsystem.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController

public class StartController {

    @GetMapping
    public String getAllPeople() {
        return "Booking system for sports locations!";
    }
}
