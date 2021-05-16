package com.bookingsystem.services;

import com.bookingsystem.models.Announcement;
import com.bookingsystem.models.AnnouncementIdAndTitle;
import com.bookingsystem.models.AnnouncementIdAndTitleInt;
import com.bookingsystem.repository.AnnouncementRepository;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public Optional<Announcement> findByTitle(String title) {
        return announcementRepository.findByTitle(title);
    }

    public Optional<Announcement> findById(UUID id) {
        return announcementRepository.findById(id);
    }

    public List<Map<String, Object>> getAnnouncementIdAndTitle() {
        return announcementRepository.getAnnouncementIdAndTitle();
    }
}
