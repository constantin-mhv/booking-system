package com.bookingsystem;

import com.bookingsystem.repository.AnnouncementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class BookingSystemApplicationTests {
	@Autowired
	AnnouncementRepository announcementRepository;

	@Test
	void contextLoads() {
//		List<Map<String, Object>> l = announcementRepository.getAnnouncementIdAndTitle();
//		System.out.println(l.get(0).keySet().toString());
	}

}
