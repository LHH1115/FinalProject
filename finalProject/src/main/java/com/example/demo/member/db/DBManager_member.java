package com.example.demo.member.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.member.vo.MemberVO;

public class DBManager_member {
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
	
	public static MemberVO findById(String id) {
		MemberVO b = null;
		SqlSession session = sqlSessionFactory.openSession();
		b = session.selectOne("member.findById",id);
		session.close();
		return b;
	}
	
	public static String findId(String name, String email) {
		MemberVO m = null;
		String id = "";
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("email", email);
		
		SqlSession session = sqlSessionFactory.openSession();
		m = session.selectOne("member.findId", map);
		id = m.getId();
		
		return id;
		
	}
	
	public static int chagePwd(String id,String pwd) {
		int re = -1;
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("pwd", pwd);
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.update("member.changePwd", map);
		session.close();
		return re;
	}
	
}
