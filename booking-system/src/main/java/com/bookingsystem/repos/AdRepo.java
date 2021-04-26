package com.bookingsystem.repos;

import com.bookingsystem.model.Ad;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdRepo extends CrudRepository<Ad, Long> {

    List<Ad> findByTitle(String title);

}
