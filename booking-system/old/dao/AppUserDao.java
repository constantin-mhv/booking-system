package com.bookingsystem.dao;

import com.bookingsystem.model.AppUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppUserDao {

    int insertAppUser(UUID id, AppUser AppUser);

    default int insertAppUser(AppUser AppUser) {
        UUID id = UUID.randomUUID();
        return insertAppUser(id, AppUser);
    }

    List<AppUser> selectAllPeople();

    Optional<AppUser> selectAppUserById(UUID id);

    int deleteAppUserById(UUID id);

    int updateAppUserById(UUID id, AppUser AppUser);

}
