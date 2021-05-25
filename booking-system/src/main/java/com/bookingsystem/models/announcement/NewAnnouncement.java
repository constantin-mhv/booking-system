package com.bookingsystem.models.announcement;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class NewAnnouncement {
    @NotBlank
    String title;
    @NotBlank
    String description;

    String country;
    String city;
    String sportType;

    List<Image> images;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getSportType() {
        return sportType;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    @Override
    public String toString() {
        return "NewAnnouncement{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                '}';
    }
}
