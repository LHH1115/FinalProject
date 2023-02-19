package com.example.demo.accommodation.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.accommodation.vo.AccommoPhotoVO;
import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.accommodation.vo.LikeVO;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/db/accommodation/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static AccommodationVO findById(int accommoNo) {
		SqlSession session = sqlSessionFactory.openSession();
		AccommodationVO a = session.selectOne("accommo.findById", accommoNo);
		session.close();
		return a;
	}
	
	public static List<AccommodationVO> findByCategory(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		List<AccommodationVO> list = session.selectList("accommo.findByCategory", map);
		session.close();
		return list;
	}
	
	public static int findCountByCategory(String keyword) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession();
		re= session.selectOne("accommo.findCountByCategory", keyword);
		session.close();
		return re;
	}
	
	public static List<AccommodationVO> findByAny(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		List<AccommodationVO> list = session.selectList("accommo.findByAny", map);
		session.close();
		return list;
	}
	
	public static int findCountByAny(String keyword) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession();
		re= session.selectOne("accommo.findCountByAny", keyword);
		session.close();
		return re;
	}
	
	public static int findPCnt(AccommoPhotoVO a) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.selectOne("accommoPhoto.findPCnt", a);
		session.close();
		return cnt;
	}
	
	public static List<AccommodationVO> findAllPhotoById(int accommoNo) {
		SqlSession session = sqlSessionFactory.openSession();
		List<AccommodationVO> list = session.selectList("accommo.findAllPhotoById", accommoNo);
		session.close();
		return list;
	}
	
	public static List<LikeVO> findMostLike(int count){
		SqlSession session = sqlSessionFactory.openSession();
		List<LikeVO> list = session.selectList("like.findMostLike", count);
		session.close();
		return list;
	}
	
}






























