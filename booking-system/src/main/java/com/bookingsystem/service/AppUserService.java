package com.bookingsystem.service;

import com.bookingsystem.dao.AppUserDao;
import com.bookingsystem.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserDao AppUserDao;

    @Autowired
    public AppUserService(@Qualifier("mysql") AppUserDao AppUserDao) {
        this.AppUserDao = AppUserDao;
    }

    public int addAppUser(AppUser AppUser) {
        return AppUserDao.insertAppUser(AppUser);
    }

    public List<AppUser> getAllPeople() {
        return AppUserDao.selectAllPeople();
    }

    public Optional<AppUser> getAppUserByID(UUID id) {
        return AppUserDao.selectAppUserById(id);
    }

    public int deleteAppUser(UUID id) {
        return AppUserDao.deleteAppUserById(id);
    }

    public int updateAppUser(UUID id, AppUser newAppUser) {
        return AppUserDao.updateAppUserById(id, newAppUser);
    }
}
