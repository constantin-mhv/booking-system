package com.bookingsystem.dao;

import com.bookingsystem.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("mysql")
public class AppUserDataAccessService implements AppUserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AppUserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertAppUser(UUID id, AppUser AppUser) {
        return 0;
    }

    @Override
    public int insertAppUser(AppUser AppUser) {
        return 0;
    }

    @Override
    public List<AppUser> selectAllPeople() {
        final String sql = "SELECT BIN_TO_UUID(id) id, email, name, password FROM app_user";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String email = resultSet.getString("email");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            return new AppUser(id, email, name, password);
        });
    }

    @Override
    public Optional<AppUser> selectAppUserById(UUID id) {
        final String sql = "SELECT BIN_TO_UUID(id) id, email, name, password from app_user where BIN_TO_UUID(id) = ?";

        AppUser appUser = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id.toString()}, (resultSet, i) -> {
                    UUID personID = UUID.fromString(resultSet.getString("id"));
                    String email = resultSet.getString("email");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    return new AppUser(personID, email, name, password);
                });
        return Optional.ofNullable(appUser);
    }

    @Override
    public int deleteAppUserById(UUID id) {
        return 0;
    }

    @Override
    public int updateAppUserById(UUID id, AppUser AppUser) {
        return 0;
    }

}
