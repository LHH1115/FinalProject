package com.example.demo.db;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.MemberVO;

public class DBManager {
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
	
	public static int insertMember(MemberVO m) {
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.insert("member.insert",m);
		session.close();
		return re;
	}
	
	public static int chk_id(String id) {
		int re = 0;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.selectOne("member.id_chk",id);
		session.close();
		return re;
	}
	
}
