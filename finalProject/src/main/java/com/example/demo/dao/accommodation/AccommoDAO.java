package com.example.demo.dao.accommodation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.accommodation.DBManager;
import com.example.demo.vo.accommodation.AccommoPhotoVO;
import com.example.demo.vo.accommodation.AccommodationVO;

@Repository
public class AccommoDAO {
	
	public List<AccommodationVO> findByCategory(String keyword) {
		// TODO Auto-generated method stub
		return DBManager.findByCategory(keyword);
		
	}
	
	public int findPCnt(AccommoPhotoVO a) {
		return DBManager.findPCnt(a);
	}
}
