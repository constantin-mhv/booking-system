package com.bookingsystem.controllers;

import com.bookingsystem.models.announcement.Announcement;
import com.bookingsystem.models.announcement.EStatus;
import com.bookingsystem.models.announcement.SelectCondition;
import com.bookingsystem.repository.AnnouncementRepository;
import com.bookingsystem.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        for (Announcement a : announcementList) {
            result.add(a.getMapLess());
        }
        return result;
    }



    @GetMapping("/list")
    public List<Map<String, Object>> getAllAnnouncementsListSelect(
            @Valid @NotNull @RequestBody SelectCondition selectCondition) {
        Sort.Direction sort = null;
        if (selectCondition.getSortCondition().equals("ASC"))
            sort = Sort.Direction.ASC;
        if (selectCondition.getSortCondition().equals("DESC"))
            sort = Sort.Direction.DESC;
        assert sort != null;
        List<Map<String, Object>> result =
                announcementRepository.findAnnouncementsWithCond(
                        selectCondition.getCountry(),
                        selectCondition.getCity(),
                        selectCondition.getSportType(),
                        selectCondition.getPriceMin(),
                        selectCondition.getPriceMax(),
                        PageRequest.of(0, 100, Sort.by(sort, selectCondition.getSortTarget()))
                );
        for (Map<String, Object> a : result)
            System.out.println(a.get("title"));
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

    @GetMapping(path = "/debug/get")
    public List<Map<String, Object>> getAllAnnouncementsSelect(@Valid @NotNull @RequestBody
                                                                       SelectCondition selectCondition) {

        return null;
    }
}
