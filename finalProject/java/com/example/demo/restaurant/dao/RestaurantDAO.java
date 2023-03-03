package com.example.demo.restaurant.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.restaurant.db.DBManager;
import com.example.demo.restaurant.vo.LikeVO;
import com.example.demo.restaurant.vo.RestaurantPhotoVO;
import com.example.demo.restaurant.vo.RestaurantVO;



@Repository
public class RestaurantDAO {
	
	public RestaurantVO findById(int restauNo) {
		return DBManager.findById(restauNo);
	}
	
	public List<RestaurantVO> findByCategory(HashMap<String, Object> map) {
		return com.example.demo.restaurant.db.DBManager.findByCategory(map);	
	}
	
	public int findCountByCategory(String keyword) {
		return DBManager.findCountByCategory(keyword);
	}
	
	public List<RestaurantVO> findByAny(HashMap<String, Object> map) {
		return DBManager.findByAny(map);	
	}
	
	public int findCountByAny(String keyword) {
		return DBManager.findCountByAny(keyword);
	}
	
	public int findPCnt(RestaurantPhotoVO a) {
		return DBManager.findPCnt(a);
	}
	
	public List<RestaurantVO> findAllPhotoById(int restauNo) {
		return DBManager.findAllPhotoById(restauNo);
	}
	
	public List<LikeVO> findMostLike(int count){
		return DBManager.findMostLike(count);
	}

	public LikeVO findLikeByM(HashMap<String, Object> map) {
		return DBManager.findLikeByM(map);
	}
	
	public int doLike(LikeVO l) {
		return DBManager.doLike(l);
	}
	
	public int unLike(LikeVO l) {
		return DBManager.unLike(l);
	}

	public int updateById(RestaurantVO r) {
		// TODO Auto-generated method stub
		return DBManager.updateById(r);
	}

	public int deleteById(int restauNo) {
		return DBManager.deleteById(restauNo);
	}

	public int insertPhoto(RestaurantPhotoVO rp) {
		return DBManager.insertPhoto(rp);
		
	}

	public List<RestaurantVO> detailSearch(HashMap<String, Object> map) {
		return DBManager.detailSearch(map);
	}

	public int findCountBydetailSearch(HashMap<String, Object> map) {
		return DBManager.findCountBydetailSearch(map);
	}
	
	
}
