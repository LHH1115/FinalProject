package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager_myPage;
import com.example.demo.vo.AccommodationVO;
import com.example.demo.vo.EventVO;
import com.example.demo.vo.InquiryVO;
import com.example.demo.vo.LikeVO;
import com.example.demo.vo.MemberVO;
import com.example.demo.vo.ReplyVO;
import com.example.demo.vo.ReservationVO;
import com.example.demo.vo.ReviewVO;




@Repository
public class MyPageDAO {
	public int updateInfo(MemberVO m) {
		return DBManager_myPage.updateInfo(m);
	}
	
	public List<EventVO> findMyPoint(int memberno) {
		return DBManager_myPage.findMyPoint(memberno);
	}
	
	public List<ReservationVO> findMyReserv(int memberno) {
		return DBManager_myPage.findMyReserv(memberno);
	}
	
	public AccommodationVO findAcc(int accommono) {
		return DBManager_myPage.findAccommo(accommono);
	}
	
	public int insertReview(ReviewVO r) {
		return DBManager_myPage.insertReview(r);
	}
	
	public int findReview(int accommono,int memberno) {
		return DBManager_myPage.findReview(accommono, memberno);
	}
	
	public List<LikeVO> findMyLike(int memberno, String category) {
		return DBManager_myPage.findMyLike(memberno, category);
	}
	
	public String findaccphoto(int accommoNo) {
		return DBManager_myPage.findaccphoto(accommoNo);
	}
	
	public int roulette_count(int memberno) {

		return DBManager_myPage.roulette_count(memberno);
	}
	
	public int point_update(int memberno, int point) {

		return DBManager_myPage.point_update(memberno, point);
	}
	
	public int point_insert(int memberno, int point) {

		return DBManager_myPage.point_insert(memberno, point);
	}
	
	public List<InquiryVO> findMyInquiry(int memberno) {
		return DBManager_myPage.findMyInquiry(memberno);
	}
	
	public InquiryVO findMyInquiryByNo(int inquiryno) {
		return DBManager_myPage.findMyInquiryByNo(inquiryno);
	}
	
	public ReplyVO findMyReply(int inquiryno) {
		return DBManager_myPage.findMyReply(inquiryno);
	}
	
	public int updateInquiry(InquiryVO i) {
		return DBManager_myPage.updateInquiry(i);
	}
	
	public int deleteInquiry(int inquiryNo) {
		return DBManager_myPage.deleteInquiry(inquiryNo);
	}
	
	

}
