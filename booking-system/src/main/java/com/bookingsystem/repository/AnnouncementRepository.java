package com.bookingsystem.repository;

import com.bookingsystem.models.Announcement;
import com.bookingsystem.models.AnnouncementIdAndTitle;
import com.bookingsystem.models.AnnouncementIdAndTitleInt;
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

	@Query(value = "select bin_to_uuid(a.id) id, a.title, a.description from announcements a where bin_to_uuid(a.id) = ?1", nativeQuery = true)
	List<Map<String, Object>> getTitleDescriptionById(UUID id);
}
