package com.example.demo.db.accommodation;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.accommodation.AccommoPhotoVO;
import com.example.demo.vo.accommodation.AccommodationVO;

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
	
	public static List<AccommodationVO> findByCategory(String keyword) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		List<AccommodationVO> list = session.selectList("accommo.findByCategory", keyword);
		session.close();
		return list;
	}
	
	public static int findPCnt(AccommoPhotoVO a) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.selectOne("accommoPhoto.findPCnt", a);
		session.close();
		return cnt;
	}
	
}






























