package com.example.demo.accommodation.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.accommodation.db.DBManager;
import com.example.demo.accommodation.vo.AccommoPhotoVO;
import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.accommodation.vo.LikeVO;
import com.example.demo.accommodation.vo.ReservationVO;

@Repository
public class AccommoDAO {
	
	public AccommodationVO findById(int accommoNo) {
		return DBManager.findById(accommoNo);
	}
	
	public List<AccommodationVO> findByCategory(HashMap<String, Object> map) {
		return DBManager.findByCategory(map);	
	}
	
	public int findCountByCategory(String keyword) {
		return DBManager.findCountByCategory(keyword);
	}
	
	public List<AccommodationVO> findByAny(HashMap<String, Object> map) {
		return DBManager.findByAny(map);	
	}
	
	public int findCountByAny(String keyword) {
		return DBManager.findCountByAny(keyword);
	}
	
	public int findPCnt(AccommoPhotoVO a) {
		return DBManager.findPCnt(a);
	}
	
	public List<AccommodationVO> findAllPhotoById(int accommoNo) {
		return DBManager.findAllPhotoById(accommoNo);
	}
	
	public List<LikeVO> findMostLike(int count){
		return DBManager.findMostLike(count);
	}
	
	public int makeReservation(ReservationVO r) {
		return DBManager.makeReservation(r);
	}
	
	public LikeVO findLikeByM(HashMap<String, Object> map){
		return DBManager.findLikeByM(map);
	}
	
	public int doLike(LikeVO l) {
		return DBManager.doLike(l);
	}
	
	public int unLike(LikeVO l) {
		return DBManager.unLike(l);
	}
	
	public int updateById(AccommodationVO a) {
		return DBManager.updateById(a);
	}
	
	public int deleteById(int accommoNo) {
		return DBManager.deleteById(accommoNo);
	}
}
