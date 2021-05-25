package com.bookingsystem.controllers;

import com.bookingsystem.models.announcement.Announcement;
import com.bookingsystem.models.announcement.NewAnnouncement;
import com.bookingsystem.models.announcement.Image;
import com.bookingsystem.models.user.User;
import com.bookingsystem.security.response.MessageResponse;
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
        for(Announcement a : announcementList) {
            result.add(Map.of(
                    "id", a.getId().toString(),
                    "title", a.getTitle(),
                    "sportType", a.getSportTypeString(),
                    "onwer_id", a.getOwner().getId().toString()
            ));
        }

//        List<Map<String, Object>> result = announcementService.getAnnouncementIdAndTitle();
        return result;
    }

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
                    "displayName", announcement.getOwner().getDisplayName(),
                    "publication_date_time", announcement.getPublicationDateTime().toString(),
                    "images", announcement.getListUrlImages(),
                    "country", announcement.getCountry(),
                    "sportType", announcement.getSportTypeString(),
                    "city", announcement.getCity()
            );
        }
    }

}
