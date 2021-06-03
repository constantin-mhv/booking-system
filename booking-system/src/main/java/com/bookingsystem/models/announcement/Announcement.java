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
    private float price;
    @Enumerated(EnumType.STRING)
    private ESport sportType;
    /* UTC time */
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDateTime;

/*    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime currentDateTime;*/

    private long dayStart;
    private long dayEnd;
    @Size(max = 10)
    private String weekdays;

    public Announcement() {
    }

    public Announcement(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Announcement(String title, String description, List<Image> images, String country, String city,
                        String sportType, Float price, long dayStart, long dayEnd, String weekdays) {
        this.title = title;
        this.description = description;
        this.images = images;
        if (country != null)
            this.country = country;
        else this.country = "Romania";
        if (city != null)
            this.city = city;
        else this.city = "Bucuresti2";
        if (sportType != null)
            this.sportType = ESport.valueOf(sportType);
        else
            this.sportType = ESport.NO_TYPE;
        if (price == null)
            this.price = -1;
        else this.price = price;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.weekdays = weekdays;
    }

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
        return city;
    }

    public ESport getSportType() {
        return sportType;
    }

    public String getSportTypeString() {
        if (sportType == null)
            return null;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getDayStart() {
        return dayStart;
    }

    public long getDayEnd() {
        return dayEnd;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setDayStart(long dayStart) {
        this.dayStart = dayStart;
    }

    public void setDayEnd(long dayEnd) {
        this.dayEnd = dayEnd;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    @Override
    public String toString() {

        if (id != null) {
            return "Ad{" +
                    "id=" + id.toString() +
                    ", owner=" + owner +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        } else {
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
        for (Image img : images) {
            result.add(img.getUrl());
        }
        return result;
    }

    public Map<String, Object> getMap() {
        return Map.ofEntries(
                Map.entry("title", getTitle()),
                Map.entry("description", getDescription()),
                Map.entry("owner_id", getOwner().getId()),
                Map.entry("displayName", getOwner().getDisplayName()),
                Map.entry("publication_date_time", getPublicationDateTime().toString()),
                Map.entry("images", getListUrlImages()),
                Map.entry("country", getCountry()),
                Map.entry("sportType", getSportTypeString()),
                Map.entry("city", getCity()),
                Map.entry("price", getPrice())
        );
    }

    public Map<String, Object> getMapLess() {
        return Map.ofEntries(
                Map.entry("id", getId().toString()),
                Map.entry("title", getTitle()),
                Map.entry("owner_id", getOwner().getId()),
                Map.entry("publication_date_time", getPublicationDateTime().toString()),
                Map.entry("sportType", getSportTypeString()),
                Map.entry("price", getPrice())
        );
    }

    public void update(NewAnnouncement changedAnnouncement) {
        setTitle(changedAnnouncement.getTitle());
        setDescription(changedAnnouncement.getDescription());
        setCity(changedAnnouncement.getCity());
        setCountry(changedAnnouncement.getCountry());
        setImages(changedAnnouncement.getImages());
        setPrice(changedAnnouncement.getPrice());
        setSportType(ESport.valueOf(changedAnnouncement.getSportType()));
        if (status == EStatus.ACTIVE)
            setStatus(EStatus.WAITING_ACCEPTANCE);
    }


}
