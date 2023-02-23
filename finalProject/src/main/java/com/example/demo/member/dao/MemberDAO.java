package com.example.demo.member.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.member.db.DBManager;
import com.example.demo.member.vo.MemberVO;

@Repository
public class MemberDAO {
	
	public MemberVO findByNo(int memberNo) {
		return DBManager.findByNo(memberNo);
	}
}
