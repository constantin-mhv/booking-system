package com.bookingsystem.models.Announcement;

import com.bookingsystem.models.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
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
    @JoinColumn(name = "owner_id")
    private User owner;

    private String title;
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

}
