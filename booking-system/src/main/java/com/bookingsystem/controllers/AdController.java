package com.bookingsystem.controllers;

import com.bookingsystem.models.Ad;
import com.bookingsystem.repository.AdRepository;
import com.bookingsystem.security.services.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ads")
public class AdController {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdService adService;

    @PostMapping("/place-ad")
    public void addAppUser(@Valid @NotNull @RequestBody Ad ad) {
        System.out.println("place-ad post" + ad.toString());
        adRepository.save(ad);
    }

    @GetMapping("/place-ad")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

//    @PostMapping
//    public void addAd()
//
//
//    public Person updatePerson(@RequestParam("arg1") String arg1,
//                               @RequestParam("arg2") String arg2,
//                               @RequestBody Person input)
//



}
