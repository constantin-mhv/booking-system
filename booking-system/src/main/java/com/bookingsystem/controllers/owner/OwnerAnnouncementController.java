package com.bookingsystem.controllers.owner;

import com.bookingsystem.models.announcement.*;
import com.bookingsystem.models.user.User;
import com.bookingsystem.repository.AnnouncementRepository;
import com.bookingsystem.repository.UserRepository;
import com.bookingsystem.security.response.MessageResponse;
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('OWNER')")
@RequestMapping("/api/owner/a")
public class OwnerAnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    UserService userService;

    @PostMapping("/new")
    public ResponseEntity<?> addAppUser(Authentication authentication,
                                        @Valid @NotNull @RequestBody NewAnnouncement newAnnouncement) {
        Optional<User> user = userService.findByUsername(authentication.getName());
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND);
        }
        Announcement announcement = new Announcement(
                newAnnouncement.getTitle(),
                newAnnouncement.getDescription(),
                newAnnouncement.getImages(),
                newAnnouncement.getCountry(),
                newAnnouncement.getCity(),
                newAnnouncement.getSportType(),
                newAnnouncement.getPrice(),
                newAnnouncement.getDateStart(),
                newAnnouncement.getDateEnd(),
                newAnnouncement.getWeekdays()
        );

        for (Image img : newAnnouncement.getImages()) {
            img.setAnnouncement(announcement);
        }
        announcement.setOwner(user.get());
        announcement.setDateTime();
        announcementRepository.save(announcement);


        return ResponseEntity.ok(new MessageResponse("Announcement registered successfully!"));
    }

    @DeleteMapping(path = "/{id}")
    void deleteAnnouncement(@PathVariable("id") String id) {
        announcementRepository.deleteById(UUID.fromString(id));
    }



    @PutMapping(path = "/{id}")
    public ResponseEntity<?> changeAnnouncement(@PathVariable("id") String id, @Valid @NotNull @RequestBody NewAnnouncement changedAnnouncement) {

        announcementRepository.updateAnnouncement(
                id,
                changedAnnouncement.getTitle(),
                changedAnnouncement.getDescription(),
                changedAnnouncement.getCountry(),
                changedAnnouncement.getCity(),
                changedAnnouncement.getSportType(),
                changedAnnouncement.getPrice()
        );
        return ResponseEntity.ok(new MessageResponse("Announcement changed successfully!"));
    }

    @PutMapping(path = "/state/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id, @NotNull @RequestBody Map<String, String> parameters) {
//        if (EStatus.valueOf(status) != EStatus.WAITING_ACCEPTANCE || EStatus.valueOf(status) != EStatus.HIDDEN || EStatus.valueOf(status) != EStatus.ACTIVE)
//            return new ResponseEntity<>(
//                    HttpStatus.NOT_ACCEPTABLE
//            );
        announcementRepository.updateAnnouncementStatus(id, parameters.get("status"));
        return ResponseEntity.ok(new MessageResponse("Announcement status changed successfully!"));
    }


}
