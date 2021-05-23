package com.bookingsystem.models;

import com.bookingsystem.models.Announcement.Announcement;
import com.bookingsystem.models.Announcement.EStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
//                @UniqueConstraint(columnNames = "email")
        })
public class User {
    //	@Id
    //	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
//    private Long id;
    private UUID id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String username;

//    @NotBlank
//    @Size(max = 50)
//    @Email
//    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

//    @Enumerated(EnumType.STRING)
//    private ERoleUser role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Announcement> announcements;

    public User() {
    }

    public User(String username, /*String email,*/ String password) {
        this.username = username;
//        this.email = email;
        this.password = password;
        this.announcements = new HashSet<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                /*", email='" + email + '\'' +*/
                ", password='" + password.toString() + '\'' +
//                ", roles=" + roles. +
//                ", announcements=" + announcements +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getEmail() {
//        return email;
//    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

//    public ERoleUser getRole() {
//        return role;
//    }

//    public void setRole(String role) {
//
//        switch (role) {
//            case "ROLE_CLIENT":
//                this.role = ERoleUser.ROLE_CLIENT;
////                this.roles.add(ERole.ROLE_CLIENT);
//                break;
//            case "ROLE_OWNER":
//                this.role = ERoleUser.ROLE_OWNER;
//            case "ROLE_ADMIN":
//                this.role = ERoleUser.ROLE_ADMIN2;
//
//        }
////        this.role = role;
//    }
}
