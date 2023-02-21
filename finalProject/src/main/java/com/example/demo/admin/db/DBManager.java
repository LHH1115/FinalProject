package com.example.demo.admin.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.admin.vo.MemberVO;
import com.example.demo.admin.vo.ReservationVO;
import com.example.demo.admin.vo.ReviewVO;

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
	public static int getTotalRecord(HashMap<String, Object> map2) {
		int cnt = 0;
		SqlSession sqlSession = sessionFactory.openSession();
		cnt = sqlSession.selectOne("member.totalRecord",map2);
		sqlSession.close();
		return cnt;
	}
		
	// 각 회원마다 리뷰 조회
	public static List<ReviewVO> memberReviewList(int memberNo){
		List<ReviewVO> memberReviewList = null;
		SqlSession sqlSession = sessionFactory.openSession();
		memberReviewList = sqlSession.selectList("member.memberReviewList",memberNo);
		sqlSession.close();
		return memberReviewList;
	}
	
	// 각 회원마다 예약 조회
		public static List<ReservationVO> memberReservationList(int memberNo){
			List<ReservationVO> memberReviewList = null;
			SqlSession sqlSession = sessionFactory.openSession();
			memberReviewList = sqlSession.selectList("member.memberReservationList",memberNo);
			sqlSession.close();
			return memberReviewList;
		}
	
}






























