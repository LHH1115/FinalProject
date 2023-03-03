package com.example.demo.member.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.member.db.DBManager_member;
import com.example.demo.member.db.DBManager_point;
import com.example.demo.member.vo.MemberVO;

@Repository
public class PointDAO {
	public int resetCount() {
		return DBManager_point.countReset();
	}
	
	
	

}
