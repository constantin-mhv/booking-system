package com.bookingsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class AppUser {
    private final UUID id;

    @NotBlank
    private final String email;

    @NotBlank
    private final String name;

    @NotBlank
    private final String password;

    public AppUser(@JsonProperty("id") UUID id,
                   @JsonProperty("email") String email,
                   @JsonProperty("name") String name,
                   @JsonProperty("password") String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
