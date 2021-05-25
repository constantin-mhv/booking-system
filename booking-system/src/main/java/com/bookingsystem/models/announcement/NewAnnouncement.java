package com.bookingsystem.models.announcement;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class NewAnnouncement {
    @NotBlank
    String title;
    @NotBlank
    String description;

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

    @Override
    public String toString() {
        return "NewAnnouncement{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                '}';
    }
}
