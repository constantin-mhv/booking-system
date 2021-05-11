package com.bookingsystem.security.services;

import com.bookingsystem.models.Ad;
import com.bookingsystem.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

//    public AdService(@Qualifier("mysql") AppUserDao AppUserDao) {
//        this.AppUserDao = AppUserDao;
//    }

    Optional<Ad> findByTitle(String title) {
        return adRepository.findByTitle(title);
    }
}
