package com.bookingsystem.controllers;

import com.bookingsystem.models.Announcement;
import com.bookingsystem.payload.response.MessageResponse;
import com.bookingsystem.repository.AnnouncementRepository;
import com.bookingsystem.security.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/new")
    public ResponseEntity<?> addAppUser(@Valid @NotNull @RequestBody Announcement announcement) {
        System.out.println("place-ad post" + announcement.toString());
        announcementRepository.save(announcement);
        return ResponseEntity.ok(new MessageResponse("Announcement registered successfully!"));
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

//    @PostMapping
//    public void addAd()
//
//
//    public Person updatePerson(@RequestParam("arg1") String arg1,
//                               @RequestParam("arg2") String arg2,
//                               @RequestBody Person input)
//



}
