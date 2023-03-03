package com.example.demo.rentcar.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.rentcar.vo.RentcarVO;


public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/rentcar/db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static RentcarVO findByCarno(int carno) {
		SqlSession session = sqlSessionFactory.openSession();
		RentcarVO vo = session.selectOne("rentcar.findByCarno",carno);
		session.close();
		return vo;
	}
	
	public static List<String> findRentByCarno(int carno) {
		SqlSession session = sqlSessionFactory.openSession();
		List<String> list = session.selectList("rentcar.findRentByCarno",carno);
		session.close();
		return list;
	}
	
	public static RentcarVO findRentInfoByStorename(String name) {
		SqlSession session = sqlSessionFactory.openSession();
		RentcarVO vo=session.selectOne("rentcar.findRentInfoByStorename",name);
		return vo;
	}
	
	public static List<RentcarVO> findStoreInfo(int no) {
		SqlSession session = sqlSessionFactory.openSession();
		List<RentcarVO> list=session.selectList("rentcar.findStoreInfo",no);
		return list;
	}
	
	public static List<Integer> findPopularCar() {
		SqlSession session = sqlSessionFactory.openSession();
		List<Integer> list=session.selectList("rentcar.findPopularCar");
		return list;
	}
	
	public static List<Integer> findSameStarCar(int no) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Integer> list=session.selectList("rentcar.findSameStarCar",no);
		return list;
	}
	
	public static int updateById(RentcarVO vo) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re=session.update("rentcar.updateById",vo);
		return re;
	}
	
	public static int deleteById(int no) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re=session.delete("rentcar.deleteById", no);
		return re;
	}
	
	public static List<RentcarVO> searchC(HashMap<String, Object> map){
		SqlSession session = sqlSessionFactory.openSession();
		List<RentcarVO> list = session.selectList("rentcar.searchC",map);
		return list;
	}
	
	public static int countC(String category) {
		SqlSession session = sqlSessionFactory.openSession();
		int re=session.selectOne("rentcar.countC", category);
		return re;
	}
	
	public static List<RentcarVO> searchName(HashMap<String, Object> map){
		SqlSession session = sqlSessionFactory.openSession();
		List<RentcarVO> list = session.selectList("rentcar.searchName",map);
		return list;
	}
	
	public static int countName(String modelname) {
		SqlSession session = sqlSessionFactory.openSession();
		int re=session.selectOne("rentcar.countName", modelname);
		return re;
	}
	
	public static List<RentcarVO> searchCatAndName(HashMap<String, Object> map){
		SqlSession session = sqlSessionFactory.openSession();
		List<RentcarVO> list = session.selectList("rentcar.searchCatAndName",map);
		return list;
	}
	
	public static int countCatAndName(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		int re=session.selectOne("rentcar.countCatAndName", map);
		return re;
	}
	
//	public static int doLike(LikeVO l) {
//	int re = 0;
//	SqlSession session = sqlSessionFactory.openSession(true);
//	re = session.insert("like.doLike", l);
//	session.close();
//	return re;
//	}
	
	
//	public static List<AccommodationVO> findByCategory(HashMap<String, Object> map) {
//		SqlSession session = sqlSessionFactory.openSession();
//		List<AccommodationVO> list = session.selectList("accommo.findByCategory", map);
//		session.close();
//		return list;
//	}
	
//	public static int findCountByCategory(String keyword) {
//		int re = 0;
//		SqlSession session = sqlSessionFactory.openSession();
//		re= session.selectOne("accommo.findCountByCategory", keyword);
//		session.close();
//		return re;
//	}
	
//	public static List<AccommodationVO> findByAny(HashMap<String, Object> map) {
//		SqlSession session = sqlSessionFactory.openSession();
//		List<AccommodationVO> list = session.selectList("accommo.findByAny", map);
//		session.close();
//		return list;
//	}
//	
//	public static int findCountByAny(String keyword) {
//		int re = 0;
//		SqlSession session = sqlSessionFactory.openSession();
//		re= session.selectOne("accommo.findCountByAny", keyword);
//		session.close();
//		return re;
//	}
	
	
	
//	public static List<LikeVO> findMostLike(int count){
//		SqlSession session = sqlSessionFactory.openSession();
//		List<LikeVO> list = session.selectList("like.findMostLike", count);
//		session.close();
//		return list;
//	}
//	
//	public static LikeVO findLikeByM(HashMap<String, Object> map){
//		SqlSession session = sqlSessionFactory.openSession();
//		LikeVO l = session.selectOne("like.findLikeByM", map);
//		session.close();
//		return l;
//	}
//	

//	
//	public static int unLike(LikeVO l) {
//		int re = 0;
//		SqlSession session = sqlSessionFactory.openSession(true);
//		re = session.delete("like.unLike", l);
//		session.close();
//		return re;
//	}
	
}






























