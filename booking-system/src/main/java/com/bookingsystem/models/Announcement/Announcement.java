package com.bookingsystem.models.Announcement;

import com.bookingsystem.models.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

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
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    @Size(max = 120)
    private String title;
    @Size(max = 4000)
    private String description;
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.WAITING_ACCEPTANCE;

    /* UTC time */
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDateTime;

/*    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime currentDateTime;*/

    public Announcement() {
    }

    public Announcement(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Announcement(String title, String description, List<Image>images) {
        this.title = title;
        this.description = description;
        this.images = images;
//        this.id = UUID.randomUUID();
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

    public void setDateTime() {
        publicationDateTime = new Date();
//        LocalDateTime d = LocalDateTime.now(DateTimeZone.forID("Europe/Bucharest"));
//        localDateTime = d.toDate();
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public EStatus getStatus() {
        return status;
    }

    public Date getPublicationDateTime() {
        return publicationDateTime;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


    @Override
    public String toString() {

        if(id != null) {
            return "Ad{" +
                    "id=" + id.toString() +
                    ", owner=" + owner +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
        else {
            return "Ad{" +
                    "id=null" +
                    ", owner=" + owner +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public List<String> getListUrlImages() {
        List<String> result = new ArrayList<>();
        for(Image img : images) {
            result.add(img.getUrl());
        }
        return result;
    }



}
