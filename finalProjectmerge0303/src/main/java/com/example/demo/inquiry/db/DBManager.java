package com.example.demo.inquiry.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.inquiry.vo.InquiryVO;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/inquiry/db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static int getNextNo() {
		SqlSession session=sqlSessionFactory.openSession();
		int no=session.selectOne("inquiry.getNextNo");
		session.close();
		return no;
	}
	
	public static List<InquiryVO> findAll(HashMap<String, Object> map){
		List<InquiryVO> list=null;
		SqlSession session = sqlSessionFactory.openSession();
		list=session.selectList("inquiry.findAll",map);
		return list;
	}
	
	public static InquiryVO findByInquiryNo(int no) {
		SqlSession session = sqlSessionFactory.openSession();
		InquiryVO vo = session.selectOne("inquiry.findByInquiryNo",no);
		return vo;
	}
	
	public static int getTotalRecord(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		int re=session.selectOne("inquiry.countAll",map);
		return re;
	}
	
	public static int insertInquiry(InquiryVO vo) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re = session.insert("inquiry.insertInquiry",vo);
//		session.commit();
		return re;
	}
	
	public static int updateReplyOk(int no) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re=session.update("inquiry.updateReplyOk",no);
		return re;
	}
	
	public static int updateReplyNo(int no) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re=session.update("inquiry.updateReplyNo",no);
		return re;
	}
	
	
	

}






























