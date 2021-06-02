package com.bookingsystem.controllers;

import com.bookingsystem.models.announcement.Announcement;
import com.bookingsystem.repository.AnnouncementRepository;
import com.bookingsystem.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('CLIENT') or hasRole('OWNER') or hasRole('ADMIN')")
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list/all")
    public List<Map<String, Object>> getAllAnnouncementsList() {
        List<Announcement> announcementList = announcementRepository.getAllAnnouncements();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Announcement a : announcementList) {
            result.add(a.getMapLess());
        }
        return result;
    }

    @GetMapping(path = "/a/{id}")
    public Map<String, Object> getAnnouncementById(@PathVariable("id") String id) {
        Optional<Announcement> a = announcementService.findById(UUID.fromString(id));
        if (a.isEmpty()) {
            return Collections.emptyMap();
        } else {
            Announcement announcement = a.get();
            return announcement.getMap();
        }
    }

}
