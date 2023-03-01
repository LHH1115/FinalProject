package com.example.demo.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.dao.PointDAO;

import lombok.Setter;

@EnableScheduling
@Component
@Setter
public class PointSchedule {

	@Autowired
	private PointDAO dao;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void resetCount() {
		dao.resetCount();
	}
}
