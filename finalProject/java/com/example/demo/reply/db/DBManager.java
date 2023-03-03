package com.example.demo.reply.db;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.reply.vo.ReplyVO;


public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/reply/db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static int getNextNo() {
		SqlSession session=sqlSessionFactory.openSession();
		int no=session.selectOne("reply.getNextNo");
		session.close();
		return no;
	}
		
	public static int insertReply(ReplyVO vo) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re = session.insert("reply.insertReply",vo);
		return re;
	}
	
	public static int deleteReply(int no) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re = session.delete("reply.deleteReply",no);
		return re;
	}
	
	public static int updateReply(ReplyVO vo) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re = session.update("reply.updateReply",vo);
		return re;
	}
	
	public static ReplyVO findByInquiryNo(int no) {
		SqlSession session = sqlSessionFactory.openSession();
		ReplyVO vo=session.selectOne("reply.findByInquiryNo",no);
		return vo;
	}
	
	public static int countAll(int no) {
		SqlSession session = sqlSessionFactory.openSession();
		int re=session.selectOne("reply.countAll",no);
		return re;
	}
	

}






























