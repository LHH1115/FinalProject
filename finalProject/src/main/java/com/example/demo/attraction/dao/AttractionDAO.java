package com.example.demo.attraction.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.attraction.db.DBManager;
import com.example.demo.attraction.vo.AttractionPhotoVO;
import com.example.demo.attraction.vo.AttractionVO;
import com.example.demo.attraction.vo.LikeVO;

@Repository
public class AttractionDAO {
	
	public AttractionVO findById(int attractNo) {
		return DBManager.findById(attractNo);
	}
	
	public List<AttractionVO> findByCategory(HashMap<String, Object> map) {
		return com.example.demo.attraction.db.DBManager.findByCategory(map);	
	}
	
	public int findCountByCategory(String keyword) {
		return DBManager.findCountByCategory(keyword);
	}
	
	public List<AttractionVO> findByAny(HashMap<String, Object> map) {
		return DBManager.findByAny(map);	
	}
	
	public int findCountByAny(String keyword) {
		return DBManager.findCountByAny(keyword);
	}
	
	public int findPCnt(AttractionPhotoVO a) {
		return DBManager.findPCnt(a);
	}
	
	public List<AttractionVO> findAllPhotoById(int attractNo) {
		return DBManager.findAllPhotoById(attractNo);
	}
	
	public List<LikeVO> findMostLike(int count){
		return DBManager.findMostLike(count);
	}
	
	
}
