package com.example.demo.like.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.example.demo.like.db.DBManager;
import com.example.demo.like.vo.LikeVO;

@Repository
public class LikeDAO {
	
	public int getMaxNo() {
		return DBManager.maxNo();
	}
	
	public int doLike(LikeVO vo) {
		return DBManager.doLike(vo);
	}
	
	public int unlike(LikeVO vo) {
		return DBManager.unLike(vo);
	}
	
	public LikeVO findLikeByM(HashMap<String, Object> map) {
		return DBManager.findLikeByM(map);
	}
	
	
}
