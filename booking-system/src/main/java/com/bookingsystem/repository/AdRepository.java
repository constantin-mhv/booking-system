package com.bookingsystem.repository;

import com.bookingsystem.models.Ad;
import com.bookingsystem.models.ERole;
import com.bookingsystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
	Optional<Ad> findByTitle(String title);
}
