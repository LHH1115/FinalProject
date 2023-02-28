package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager_member;
import com.example.demo.vo.MemberVO;

@Repository
public class MemberDAO {
	public int insertMember(MemberVO m) {
		return DBManager_member.insertMember(m);
	}
	
	public int chk_id(String id) {
		return DBManager_member.chk_id(id);
	}
	
	public MemberVO findById(String id) {
		return DBManager_member.findById(id);
	}
	
	public String findId(String name, String email) {
		return DBManager_member.findId(name, email);
	}
	
	public int chagePwd(String id, String pwd) {
		return DBManager_member.chagePwd(id, pwd);
	}
	
	

}
