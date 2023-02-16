package com.example.demo.admin.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.admin.vo.MemberVO;

public class DBManager {
	
	public static SqlSessionFactory sessionFactory;
	
	static {
		try {
			String resource = "com/example/demo/admin/db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	// 관리자 회원 조회
	public static List<MemberVO> listMember(HashMap<String, Object> map){
		List<MemberVO> memberlist = null;
		SqlSession sqlSession = sessionFactory.openSession();
		memberlist = sqlSession.selectList("member.findAll", map);
		sqlSession.close();
		return memberlist;
	}
	
	// 회원조회 페이징 처리
	public static int getTotalRecord() {
		int cnt = 0;
		SqlSession sqlSession = sessionFactory.openSession();
		cnt = sqlSession.selectOne("member.totalRecord");
		sqlSession.close();
		return cnt;
	}
		
	
}






























