package com.bookingsystem.api;

import com.bookingsystem.model.AppUser;
import com.bookingsystem.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/appUser")
@RestController
public class AppUserController {

    private final AppUserService AppUserService;

    @Autowired
    public AppUserController(AppUserService AppUserService) {
        this.AppUserService = AppUserService;
    }

    @PostMapping
    public void addAppUser(@Valid @NotNull @RequestBody AppUser AppUser) {
        AppUserService.addAppUser(AppUser);
    }

    @GetMapping
    public List<AppUser> getAllPeople() {
        return AppUserService.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public AppUser getAppUserById(@PathVariable("id") UUID id) {
        return AppUserService.getAppUserByID(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteAppUserById(@PathVariable("id") UUID id) {
        AppUserService.deleteAppUser(id);
    }

    @PutMapping(path = "{id}")
    public void updateAppUser(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody AppUser AppUserToUpdate) {
        AppUserService.updateAppUser(id, AppUserToUpdate);
    }
}
