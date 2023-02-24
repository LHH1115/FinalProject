package com.example.demo.attraction.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;




import com.example.demo.attraction.vo.AttractionPhotoVO;
import com.example.demo.attraction.vo.AttractionVO;
import com.example.demo.attraction.vo.LikeVO;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/attraction/db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static AttractionVO findById(int attractNo) {
		SqlSession session = sqlSessionFactory.openSession();
		AttractionVO a = session.selectOne("attract.findById", attractNo);
		session.close();
		return a;
	}
	
	public static List<AttractionVO> findByCategory(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		List<AttractionVO> list = session.selectList("attract.findByCategory", map);
		session.close();
		return list;
	}
	
	public static int findCountByCategory(String keyword) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession();
		re= session.selectOne("attract.findCountByCategory", keyword);
		session.close();
		return re;
	}
	
	public static List<AttractionVO> findByAny(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		List<AttractionVO> list = session.selectList("attract.findByAny", map);
		session.close();
		return list;
	}
	
	public static int findCountByAny(String keyword) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession();
		re= session.selectOne("attract.findCountByAny", keyword);
		session.close();
		return re;
	}
	
	public static int findPCnt(AttractionPhotoVO a) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.selectOne("attractPhoto.findPCnt", a);
		session.close();
		return cnt;
	}
	
	public static List<AttractionVO> findAllPhotoById(int attractNo) {
		SqlSession session = sqlSessionFactory.openSession();
		List<AttractionVO> list = session.selectList("attract.findAllPhotoById", attractNo);
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






























