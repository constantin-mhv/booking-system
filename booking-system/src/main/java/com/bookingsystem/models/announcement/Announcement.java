package com.bookingsystem.models.announcement;

import com.bookingsystem.models.user.User;
import org.apache.tomcat.util.security.Escape;
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

    private String country;
    private String city;
    @Enumerated(EnumType.STRING)
    private ESport sportType;
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

    public Announcement(String title, String description, List<Image>images, String country, String city) {
        this.title = title;
        this.description = description;
        this.images = images;
        this.country = country;
        this.city = city;
//        this.id = UUID.randomUUID();
    }

    public Announcement(String title, String description, List<Image>images, String country, String city,
                        String sportType) {
        this.title = title;
        this.description = description;
        this.images = images;
        if(country != null)
            this.country = country;
        else this.country = "Romania";
        if(city != null)
            this.city = city;
        else this.city = "Bucuresti2";
        if(sportType != null)
            this.sportType = ESport.valueOf(sportType);
        else
            this.sportType = ESport.NO_TYPE;
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

    public String getCountry() {
        return country;
    }

    public String getCity() {
        System.out.println(city);
        return city;
    }

    public ESport getSportType() {
        return sportType;
    }

    public String getSportTypeString() {
        if(sportType == null)
            return null;
        System.out.println(sportType.name());
        return sportType.name();
//        return sportType == null ? null : sportType.name();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSportType(ESport sportType) {
        this.sportType = sportType;
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
