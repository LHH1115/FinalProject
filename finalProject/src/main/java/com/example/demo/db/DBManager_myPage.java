package com.example.demo.db;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.AccommoPhotoVO;
import com.example.demo.vo.AccommodationVO;
import com.example.demo.vo.EventVO;
import com.example.demo.vo.LikeVO;
import com.example.demo.vo.MemberVO;
import com.example.demo.vo.ReservationVO;
import com.example.demo.vo.ReviewVO;

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
		re = session.update("mypage.updateInfo", m);
		session.close();
		return re;
	}
	
	public static List<EventVO> findMyPoint(int memberNo) {
		List<EventVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("mypage.findMyPoint", memberNo);
		session.close();
		return list;
	}
	
	public static List<ReservationVO> findMyReserv(int memberNo) {
		List<ReservationVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("mypage.findMyReserv", memberNo);
		session.close();
		return list;
	}
	
	public static AccommodationVO findAccommo(int accommono) {
		AccommodationVO acc = null;
		SqlSession session = sqlSessionFactory.openSession();
		acc = session.selectOne("mypage.findAccommo", accommono);
		session.close();
		return acc;
	}
	
	public static int insertReview(ReviewVO r) {
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.insert("mypage.insertReview", r);
		
		return re;
	}
	
	public static int findReview(int accommono, int memberno) {
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accommono", accommono);
		map.put("memberno", memberno);
		
		re = session.selectOne("mypage.findReview", map);
		System.out.println("값"+re);
		session.close();
		return re;
	}
	
	public static List<LikeVO> findMyLike(int memberNo, String category){
		List<LikeVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberno", memberNo);
		map.put("category", category);
		list = session.selectList("mypage.findMyLike", map);
		session.close();
		return list;
	}
	
	public static String findaccphoto(int accommoNo) {
		AccommoPhotoVO accp = null;
		String photopath = "";
		SqlSession session = sqlSessionFactory.openSession();
		accp = session.selectOne("mypage.findAccphoto",accommoNo);
		photopath = accp.getPath();
		session.close();
		return photopath;
				
	}
	
	public static int roulette_count(int memberno) {
		
		System.out.println("카운트감소시작");
		
		int re = -1;
		MemberVO m = new MemberVO();
		m.setMemberno(memberno);
		
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.update("mypage.useRcount", memberno);
		session.commit();
		System.out.println("카운트감소"+re);
		System.out.println("dddd");
		
		session.close();

		
		
		return re;
		
	}
	public static int point_update(int memberno, int point) {
		System.out.println("포인트업데이트시작");
		int re = -1;
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("memberno", memberno);
//		map.put("point", point);
		MemberVO m = new MemberVO();
		m.setMemberno(memberno);
		m.setPoint(point);
		
		SqlSession session = sqlSessionFactory.openSession(true);
		
		re = session.update("mypage.updatePoint", m);
		System.out.println("포인트업데이트"+re);
		session.commit();
	
		session.close();

		
		
		return re;
		
	}
	
	public static int point_insert(int memberno, int point) {
		System.out.println("포인트삽입시작");
		int re = -1;
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("memberno", memberno);
//		map.put("point", point);
		MemberVO m = new MemberVO();
		m.setMemberno(memberno);
		m.setPoint(point);
		
		
		SqlSession session = sqlSessionFactory.openSession(true);

		
		re = session.update("mypage.insertEvent", m);
		System.out.println("포인트삽입"+re);
		session.commit();
		
		
		session.close();

		
		
		return re;
		
	}
}
