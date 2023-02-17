package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.MemberVO;

@Repository
public class MemberDAO {
	public int insertMember(MemberVO m) {
		return DBManager.insertMember(m);
	}
	
	public int chk_id(String id) {
		return DBManager.chk_id(id);
	}

}
