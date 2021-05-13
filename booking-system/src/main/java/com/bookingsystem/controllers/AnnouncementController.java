package com.bookingsystem.controllers;

import com.bookingsystem.models.Announcement;
import com.bookingsystem.models.AnnouncementIdAndTitle;
import com.bookingsystem.models.AnnouncementIdAndTitleInt;
import com.bookingsystem.models.User;
import com.bookingsystem.payload.response.MessageResponse;
import com.bookingsystem.repository.AnnouncementRepository;
import com.bookingsystem.repository.UserRepository;
import com.bookingsystem.security.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * TODO: /api/announcements/list - return list with map("id", "title")
 * TODO: /api/announcements/a/ + id (UUID) - return an announcement map
 * TODO: change anouncement id from int to UUID */

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

    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addAppUser(Authentication authentication,  @Valid @NotNull @RequestBody Announcement announcement) {
        System.out.println("place-ad post" + announcement.toString());
//        System.out.println(user.toString());
        System.out.println(authentication.getName());
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND);
        }
        announcement.setOwner(user.get());
        announcementRepository.save(announcement);
        getAllAnnouncementsList();
        return ResponseEntity.ok(new MessageResponse("Announcement registered successfully!"));
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/list")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Map<String, Object>> getAllAnnouncementsList() {
        List<Map<String, Object>> result = announcementRepository.getAnnouncementIdAndTitle();
        return result;
    }

}
