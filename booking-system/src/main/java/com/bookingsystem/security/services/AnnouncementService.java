package com.bookingsystem.security.services;

import com.bookingsystem.models.Announcement;
import com.bookingsystem.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

//    public AdService(@Qualifier("mysql") AppUserDao AppUserDao) {
//        this.AppUserDao = AppUserDao;
//    }

    Optional<Announcement> findByTitle(String title) {
        return announcementRepository.findByTitle(title);
    }
}
