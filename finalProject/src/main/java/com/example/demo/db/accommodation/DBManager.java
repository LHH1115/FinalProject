package com.example.demo.db.accommodation;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.accommodation.AccommodationVO;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	
	
	public static List<AccommodationVO> findAllDept() {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		List<AccommodationVO> list = session.selectList("dept.findAll");
		session.close();
		return list;
	}
	
	public static int insertDept(AccommodationVO a) {
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.insert("dept.insert",a);
		session.close();
		return re;
	}
	
	public static int updateDept(AccommodationVO a) {
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.update("dept.update", a);
		session.close();
		return re;
	}
	
	public static int deleteDept(int accommono) {
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.delete("dept.delete", accommono);
		session.close();
		return re;
	}

	public static int insertLogData(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.insert("logData.insert",map);
		session.close();
		return re;
	}
	
}






























