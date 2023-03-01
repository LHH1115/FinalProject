package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager_member;
import com.example.demo.db.DBManager_point;
import com.example.demo.vo.MemberVO;

@Repository
public class PointDAO {
	public int resetCount() {
		return DBManager_point.countReset();
	}
	
	
	

}
