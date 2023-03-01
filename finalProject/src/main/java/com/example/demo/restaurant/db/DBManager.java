package com.example.demo.restaurant.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.restaurant.vo.LikeVO;
import com.example.demo.restaurant.vo.RestaurantPhotoVO;
import com.example.demo.restaurant.vo.RestaurantVO;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/restaurant/db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static RestaurantVO findById(int restauNo) {
		SqlSession session = sqlSessionFactory.openSession();
		RestaurantVO a = session.selectOne("restau.findById", restauNo);
		session.close();
		return a;
	}
	
	public static List<RestaurantVO> findByCategory(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		List<RestaurantVO> list = session.selectList("restau.findByCategory", map);
		session.close();
		return list;
	}
	
	public static int findCountByCategory(String keyword) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession();
		re= session.selectOne("restau.findCountByCategory", keyword);
		session.close();
		return re;
	}
	
	public static List<RestaurantVO> findByAny(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		List<RestaurantVO> list = session.selectList("restau.findByAny", map);
		session.close();
		return list;
	}
	
	public static int findCountByAny(String keyword) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession();
		re= session.selectOne("restau.findCountByAny", keyword);
		session.close();
		return re;
	}
	
	public static int findPCnt(RestaurantPhotoVO a) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.selectOne("restauPhoto.findPCnt", a);
		session.close();
		return cnt;
	}
	
	public static List<RestaurantVO> findAllPhotoById(int restauNo) {
		SqlSession session = sqlSessionFactory.openSession();
		List<RestaurantVO> list = session.selectList("restau.findAllPhotoById", restauNo);
		session.close();
		return list;
	}
	
	public static List<LikeVO> findMostLike(int count){
		SqlSession session = sqlSessionFactory.openSession();
		List<LikeVO> list = session.selectList("like.findMostLike", count);
		session.close();
		return list;
	}

	public static LikeVO findLikeByM(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		LikeVO l = session.selectOne("like.findLikeByM", map);
		session.close();
		return l;
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

	public static int updateById(RestaurantVO r) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.update("restau.updateById", r);
		session.close();
		return re;
	}

	public static int deleteById(int restauNo) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.delete("restau.deleteById", restauNo);
		session.close();
		return re;
	}

	public static int insertPhoto(RestaurantPhotoVO rp) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.insert("restaurantPhoto.insertPhoto", rp);
		session.close();
		return re;
	}
	
	
	
}






























