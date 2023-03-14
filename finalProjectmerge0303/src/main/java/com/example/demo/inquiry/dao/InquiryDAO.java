package com.example.demo.inquiry.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.inquiry.db.DBManager;
import com.example.demo.inquiry.vo.InquiryVO;

@Repository
public class InquiryDAO {
	
	// 관리자 회원 조회
	public List<InquiryVO> findAll(HashMap<String, Object> map){
		return DBManager.findAll(map);
	}
	
	public InquiryVO findByInquiryNo(int no) {
		return DBManager.findByInquiryNo(no);
	}
	
	// 회원 총 레코드 수
	public int getTotalRecord(HashMap<String, Object> map) {
		return DBManager.getTotalRecord(map);
	}
	
	public int getNextNo() {
		return DBManager.getNextNo();
	}
	
	public int insertInquiry(InquiryVO vo) {
		return DBManager.insertInquiry(vo);
	}
	
	public int updateReplyOk(int no) {
		return DBManager.updateReplyOk(no);
	}
	
	public int updateReplyNo(int no) {
		return DBManager.updateReplyNo(no);
	}
	
}


















