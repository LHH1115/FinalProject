package com.example.demo.dao.accommodation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.accommodation.DBManager;
import com.example.demo.vo.accommodation.AccommoPhotoVO;
import com.example.demo.vo.accommodation.AccommodationVO;
import com.example.demo.vo.accommodation.LikeVO;

@Repository
public class AccommoDAO {
	
	public AccommodationVO findById(int accommoNo) {
		return DBManager.findById(accommoNo);
	}
	
	public List<AccommodationVO> findByCategory(String keyword) {
		return DBManager.findByCategory(keyword);
		
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
}
