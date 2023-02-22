package com.example.demo.db;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.MemberVO;

public class DBManager_myPage {

public static SqlSessionFactory sqlSessionFactory;
	
	static {
		try {
			String resource = "com/example/demo/db/SqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
		}catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
	}
	
	public static int updateInfo(MemberVO m) {
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.update("updateInfo", m);
		session.close();
		return re;
	}
}
