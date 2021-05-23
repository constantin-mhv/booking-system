package com.bookingsystem.controllers;

import com.bookingsystem.models.Announcement.Announcement;
import com.bookingsystem.models.User;
import com.bookingsystem.payload.response.MessageResponse;
import com.bookingsystem.repository.AnnouncementRepository;
import com.bookingsystem.repository.UserRepository;
import com.bookingsystem.services.AnnouncementService;
import com.bookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    UserService userService;

    @PostMapping("/new")
//    @PreAuthorize("hasRole('USER1111') or hasRole('MODERATOR1111') or hasRole('ADMIN1111')")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> addAppUser(Authentication authentication, @Valid @NotNull @RequestBody Announcement announcement) {
        System.out.println("place-ad post" + announcement.toString());
        System.out.println(authentication.getName());
        Optional<User> user = userService.findByUsername(authentication.getName());
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND);
        }
        announcement.setOwner(user.get());
        announcement.setDateTime();
        announcementRepository.save(announcement);
        return ResponseEntity.ok(new MessageResponse("Announcement registered successfully!"));
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('OWNER')")
    public String userAccess() {
        return "User Content.";
    }

    @PreAuthorize("hasRole('CLIENT') or hasRole('OWNER') or hasRole('ADMIN')")
    @GetMapping("/list")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Map<String, Object>> getAllAnnouncementsList() {
        List<Map<String, Object>> result = announcementService.getAnnouncementIdAndTitle();
        return result;
    }

    @PreAuthorize("hasRole('CLIENT') or hasRole('OWNER') or hasRole('ADMIN')")
    @GetMapping(path = "/a/{id}")
    public Map<String, Object> getAnnouncementById(@PathVariable("id") String id) {
        System.out.println(id);
        Optional<Announcement> a = announcementService.findById(UUID.fromString(id));
        if (a.isEmpty()) {
            System.out.println("Not found announcement");
            return Collections.emptyMap();
        } else {
            Announcement announcement = a.get();
            return Map.of(
                    "title", announcement.getTitle(),
                    "description", announcement.getDescription(),
                    "owner_id", announcement.getOwner().getId(),
                    "username", announcement.getOwner().getUsername(),
                    "publication_date_time", announcement.getPublicationDateTime().toString());
        }
    }
}
