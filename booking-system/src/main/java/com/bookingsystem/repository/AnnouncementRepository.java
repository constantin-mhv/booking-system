package com.bookingsystem.repository;

import com.bookingsystem.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
	Optional<Announcement> findByTitle(String title);
}
