package com.example.demo.reply.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.reply.db.DBManager;
import com.example.demo.reply.vo.ReplyVO;



@Repository
public class ReplyDAO {
	
	public int getNextNo() {
		return DBManager.getNextNo();
	}
	
	public int insertReply(ReplyVO vo) {
		return DBManager.insertReply(vo);
	}
	
	public int updateReply(ReplyVO vo) {
		return DBManager.updateReply(vo);
	}
	
	public int deleteReply(int no) {
		return DBManager.deleteReply(no);
	}
	
	public ReplyVO findByInquiryNo(int no) {
		return DBManager.findByInquiryNo(no);
	}
	
	public int countAll(int no) {
		return DBManager.countAll(no);
	}

	
	
}


















