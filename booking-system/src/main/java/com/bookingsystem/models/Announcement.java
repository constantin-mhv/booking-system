package com.bookingsystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "announcements")
public class Announcement {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    private String title;
    private String description;

    public Announcement() {
    }

    public Announcement(String title, String description) {
        this.title = title;
        this.description = description;
    }

/*    public Announcement(String title, String description, User owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }*/

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", owner=" + owner +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
