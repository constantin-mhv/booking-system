package com.bookingsystem.repository;

import com.bookingsystem.models.Announcement.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
    Optional<Announcement> findByTitle(String title);

    Optional<Announcement> findById(UUID id);

    @Query(value = "select bin_to_uuid(a.id) id, a.title from announcements a", nativeQuery = true)
    List<Map<String, Object>> getAnnouncementIdAndTitle();

    @Query(value = "select bin_to_uuid(a.id) id, a.title, a.description " +
			"" +
            "from announcements a where bin_to_uuid(a.id) = ?1", nativeQuery = true)
    List<Map<String, Object>> getTitleDescriptionById(String id);

    @Query(value = "select bin_to_uuid(a.id) id, a.title, a.publication_date_time " +
            "from announcements a where bin_to_uuid(a.owner_id) = ?1", nativeQuery = true)
    List<Map<String, Object>> getTitleDateById(String id);

    @Query(value = "select bin_to_uuid(a.id) id, a.title, a.description from announcements a where a.title = ?1", nativeQuery = true)
    List<Map<String, Object>> findByTitle2(String title);
}
