package com.example.demo.like.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.like.vo.LikeVO;
import com.example.demo.rentcar.vo.RentcarVO;


public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/like/db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static int maxNo() {
	SqlSession session = sqlSessionFactory.openSession();
	int re = session.selectOne("like.maxno");
	return re;
	}
	
	public static int doLike(LikeVO l) {
	int re = 0;
	SqlSession session = sqlSessionFactory.openSession(true);
	re = session.insert("like.doLike", l);
	session.close();
	return re;
	}
	
	public static int unLike(LikeVO l) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.delete("like.unLike", l);
		session.close();
		return re;
	}
	
	
//	public static List<AccommodationVO> findByCategory(HashMap<String, Object> map) {
//		SqlSession session = sqlSessionFactory.openSession();
//		List<AccommodationVO> list = session.selectList("accommo.findByCategory", map);
//		session.close();
//		return list;
//	}
	
	public static int findCountByCategory(String keyword) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession();
		re= session.selectOne("accommo.findCountByCategory", keyword);
		session.close();
		return re;
	}
	
//	public static List<AccommodationVO> findByAny(HashMap<String, Object> map) {
//		SqlSession session = sqlSessionFactory.openSession();
//		List<AccommodationVO> list = session.selectList("accommo.findByAny", map);
//		session.close();
//		return list;
//	}
	
	public static int findCountByAny(String keyword) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession();
		re= session.selectOne("accommo.findCountByAny", keyword);
		session.close();
		return re;
	}
	
	public static List<LikeVO> findMostLike(int count){
		SqlSession session = sqlSessionFactory.openSession();
		List<LikeVO> list = session.selectList("like.findMostLike", count);
		session.close();
		return list;
	}
	
	public static LikeVO findLikeByM(HashMap<String, Object> map){
		SqlSession session = sqlSessionFactory.openSession();
		LikeVO l = session.selectOne("like.findLikeByM", map);
		session.close();
		return l;
	}
	
	

	
}






























