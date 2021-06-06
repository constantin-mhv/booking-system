package com.bookingsystem.repository;

import com.bookingsystem.models.announcement.Announcement;
import com.bookingsystem.models.announcement.EStatus;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
    Optional<Announcement> findByTitle(String title);

    Optional<Announcement> findById(UUID id);

    @Query(value = "select bin_to_uuid(a.id) id, a.title, a.owner_id from announcements a", nativeQuery = true)
    List<Map<String, Object>> getAnnouncementIdAndTitle();

    @Query(value = "select * from announcements", nativeQuery = true)
    List<Announcement> getAllAnnouncements();

    @Query(value = "select bin_to_uuid(a.id) id, a.title, a.description " +
            "" +
            "from announcements a where bin_to_uuid(a.id) = ?1", nativeQuery = true)
    List<Map<String, Object>> getTitleDescriptionById(String id);

    @Query(value = "select bin_to_uuid(a.id) id, a.title, a.publication_date_time " +
            "from announcements a where bin_to_uuid(a.owner_id) = ?1", nativeQuery = true)
    List<Map<String, Object>> getTitleDateById(String id);

    @Query(value = "select bin_to_uuid(a.id) id, a.title, a.description from announcements a where a.title = ?1", nativeQuery = true)
    List<Map<String, Object>> findByTitle2(String title);

    @Modifying
    @Transactional
    @Query(value = "update announcements a set a.title = :title, a.description =:description, " +
            "a.country = :country, a.city = :city, a.price = :price, a.sport_type = :sport_type " +
            "where bin_to_uuid(a.id) = :id", nativeQuery = true)
    void updateAnnouncement(
            @Param("id") String id,
            @Param("title") String title,
            @Param("description") String description,
            @Param("country") String country,
            @Param("city") String city,
            @Param("sport_type") String sportType,
            @Param("price") Float price);

    @Modifying
    @Transactional
    @Query(value = "update announcements a set a.status = :status " +
            "where bin_to_uuid(a.id) = :id", nativeQuery = true)
    void updateAnnouncementStatus(@Param("id") String id, @Param("status") String status);

    @Query(value = "select bin_to_uuid(id) id, bin_to_uuid(owner_id) owner_id, title, description, " +
            "country, city, price, publication_date_time, date_start, date_end, weekdays, sport_type, " +
            "status from announcements a where " +
            ":country is null or a.country = :country and " +
            ":city is null or a.city = :city and " +
            ":sport_type is null or a.sport_type = :sport_type " +
            "and (a. price BETWEEN :price_min AND :price_max)", nativeQuery = true)
    List<Map<String, Object>> findAnnouncementsWithCond(
            @Param("country") String country,
            @Param("city") String city,
            @Param("sport_type") String sportType,
            @Param("price_min") Float priceMin,
            @Param("price_max") Float priceMax,
            Pageable pageable
    );
}
