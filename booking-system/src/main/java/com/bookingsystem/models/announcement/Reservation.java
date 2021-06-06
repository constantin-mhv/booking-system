package com.bookingsystem.models.announcement;

import com.bookingsystem.models.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    String announcementId;
    Long date;
    Float price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private User client;

    public Reservation() {
    }

    public Reservation(String announcementID, Long date, Float price) {
        this.announcementId = announcementID;
        this.date = date;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public Long getDate() {
        return date;
    }

    public Float getPrice() {
        return price;
    }

    public User getClient() {
        return client;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
