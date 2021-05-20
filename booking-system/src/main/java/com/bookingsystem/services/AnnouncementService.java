package com.bookingsystem.services;

import com.bookingsystem.models.Announcement.Announcement;
import com.bookingsystem.repository.AnnouncementRepository;
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

    public List<Map<String, Object>> getTitleDateById(UUID id) {
        return announcementRepository.getTitleDateById(id.toString());
    }
}
