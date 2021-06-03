package com.bookingsystem.models.announcement;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class NewAnnouncement {
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    private String country;
    private String city;
    private String sportType;

    private long dateStart;
    private long dateEnd;
    private String weekdays;

    private Float price;

    private List<Image> images;

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public long getDateStart() {
        return dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
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
