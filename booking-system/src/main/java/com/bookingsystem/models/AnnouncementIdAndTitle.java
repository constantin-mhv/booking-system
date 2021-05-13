package com.bookingsystem.models;

import java.util.UUID;

public class AnnouncementIdAndTitle {
    private UUID Id;
    private String Title;

    public AnnouncementIdAndTitle(UUID id, String title) {
        Id = id;
        Title = title;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public UUID getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    @Override
    public String toString() {
        return "AnnouncementIdAndTitle{" +
                "Id=" + Id +
                ", Title='" + Title + '\'' +
                '}';
    }
}
