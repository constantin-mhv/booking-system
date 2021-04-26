package com.bookingsystem.controller;

import com.bookingsystem.model.Ad;
import com.bookingsystem.repos.AdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private AdRepo adRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }


    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Ad> ads = adRepo.findAll();

        model.put("ads", ads);

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String title, @RequestParam String text, Map<String, Object> model) {
        Ad ad = new Ad(title, text);

        adRepo.save(ad);

        Iterable<Ad> ads = adRepo.findAll();

        model.put("ads", ads);

        return "main";
    }


    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Ad> ads;

        if (filter != null && !filter.isEmpty()) {
            ads = adRepo.findByTitle(filter);
        } else {
            ads = adRepo.findAll();
        }

        model.put("ads", ads);

        return "main";
    }

}
