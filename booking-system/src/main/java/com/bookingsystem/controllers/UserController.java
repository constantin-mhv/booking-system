package com.bookingsystem.controllers;

import com.bookingsystem.models.Announcement;
import com.bookingsystem.models.User;
import com.bookingsystem.services.AnnouncementService;
import com.bookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")

public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AnnouncementService announcementService;

    @GetMapping("/u/{id}/list")
    public List<Map<String, Object>> getAllAnnouncementsListByUser(@PathVariable("id") UUID id) {
        Optional<User> u = userService.findById(id);
        if (u.isEmpty()) {
            System.out.println("User not found");
            return Collections.emptyList();
        } else {
            User user = u.get();
            Set<Announcement> announcements = user.getAnnouncements();
            List<Map<String, Object>> result = new ArrayList<>();
            for (Announcement a : announcements) {
                result.add(Map.of(
                        "title", a.getTitle(),
                        "id", a.getId())
                );
            }

            for (Map<String, Object> m : result) {
                System.out.println(m.get("title"));
            }
            return result;
        }
    }

    @GetMapping("/u/{id}")
    public Map<String, Object> getUserDetailsById(@PathVariable("id") UUID id) {
        Optional<User> u = userService.findById(id);
        if(u.isEmpty()) {
            System.out.println("User not found");
            return Collections.emptyMap();
        } else {
            User user = u.get();
            return Map.of(
                    "username", user.getUsername()
            );
        }
    }

}
