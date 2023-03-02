package com.example.demo.member.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager_member;
import com.example.demo.db.DBManager_point;


@Repository
public class PointDAO {
	public int resetCount() {
		return DBManager_point.countReset();
	}
	
	
	

}
