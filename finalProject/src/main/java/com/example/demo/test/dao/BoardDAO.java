package com.example.demo.test.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.test.db.DBManager;
import com.example.demo.test.vo.BoardVO;


@Repository
public class BoardDAO {
	
	
	public List<BoardVO> findAll(HashMap<String, Object> map){
		return DBManager.findAll(map);
	}
	
}


















