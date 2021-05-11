package com.bookingsystem.models;

import javax.persistence.*;

@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
