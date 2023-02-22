package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager_myPage;
import com.example.demo.vo.MemberVO;



@Repository
public class MyPageDAO {
	public int updateInfo(MemberVO m) {
		return DBManager_myPage.updateInfo(m);
	}

}
