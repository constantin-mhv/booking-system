package com.bookingsystem.controllers.client;

import com.bookingsystem.models.announcement.Announcement;
import com.bookingsystem.models.announcement.Reservation;
import com.bookingsystem.models.user.User;
import com.bookingsystem.repository.AnnouncementRepository;
import com.bookingsystem.repository.ReservationRepository;
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

import javax.validation.constraints.NotNull;
import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('CLIENT')")
@RequestMapping("/api/client/a")
public class ClientAnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    UserService userService;

    @Autowired
    ReservationRepository reservationRepository;

    @PostMapping("/book/{id}")
    public ResponseEntity<?> bookAnnouncement(@PathVariable("id") String id, Authentication authentication,
                                              @NotNull @RequestBody Map<String, Long> parameters) {
        Optional<User> u = userService.findByUsername(authentication.getName());
        Optional<Announcement> a = announcementRepository.findById(UUID.fromString(id));
        User user = null;
        if (u.isEmpty()) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND);
        } else user = u.get();

        Announcement announcement = null;
        if (a.isEmpty()) {
            return new ResponseEntity<>(
                    "Announcement not found",
                    HttpStatus.NOT_FOUND);
        } else announcement = a.get();

        Reservation reservation = new Reservation(announcement.getId().toString(), parameters.get("date"),
                announcement.getPrice());

        reservation.setClient(user);

        reservationRepository.save(reservation);
        return ResponseEntity.ok(new MessageResponse("Reservation created successfully!"));
    }

    @GetMapping("/reservations")
    List<Map<String, Object>> getReservations(Authentication authentication) {
        Optional<User> u = userService.findByUsername(authentication.getName());
        User user = null;
        if(u.isEmpty())
            return null;
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map;
        user = u.get();
        for(Reservation reservation : u.get().getReservations()) {
            Optional<Announcement> a = announcementService.findById(UUID.fromString(reservation.getAnnouncementId()));
            map = announcementService.findById(UUID.fromString(reservation.getAnnouncementId())).get().getMapLessReservation(reservation.getDate());

            result.add(map);
        }
        return result;
    }
}
