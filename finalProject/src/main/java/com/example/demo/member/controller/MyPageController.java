package com.example.demo.member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.member.dao.MemberDAO;
import com.example.demo.member.dao.MyPageDAO;

import com.example.demo.member.vo.EventVO;
import com.example.demo.member.vo.InquiryVO;
import com.example.demo.member.vo.LikeVO;

import com.example.demo.member.vo.MyLikeVO;
import com.example.demo.member.vo.ReplyVO;
import com.example.demo.member.vo.ReservationVO;
import com.example.demo.member.vo.ReviewVO;
import com.example.demo.vo.AccommodationVO;
import com.example.demo.vo.MemberVO;

@Controller
public class MyPageController {
	
	public int pageSIZE = 5;
	public int totalRecord = 0;
	public int totalPage = 1;
	
	
	
	@Autowired
	private MemberDAO m_dao;
	
	public void setmDao(MemberDAO m_dao) {
		this.m_dao = m_dao;
	}
	
	@Autowired
	private MyPageDAO mp_dao;
	
	public void setmpDao(MyPageDAO mp_dao) {
		this.mp_dao = mp_dao;
	}
	
	@GetMapping("/myPage/updateMyInfo")
	public void updateMyInfoForm(Model model,HttpSession session) {
		String id = (String) session.getAttribute("id");
		
		MemberVO m = m_dao.findById(id);
		String[] addr = m.getAddr().split("/");
		String[] jumin = m.getJumin().split("-");
		
		model.addAttribute("m", m);
		model.addAttribute("addr_f", addr[0]);
		model.addAttribute("addr_b", addr[1]);
		model.addAttribute("jumin_f", jumin[0]);
		model.addAttribute("jumin_b", jumin[1]);
		
	}
	@PostMapping("/myPage/updateMyInfo")
	public ModelAndView updateMyInfoSubmit(MemberVO m,int jumin_f, int jumin_b, String addr_f, String addr_b) {
		ModelAndView mav = new ModelAndView("redirect:/");
		m.setJumin(jumin_f+"-"+jumin_b);
		m.setAddr(addr_f+"/"+addr_b);
		

		int re = mp_dao.updateInfo(m);
		System.out.println(re);
		
		return mav;
	}
	
	@GetMapping("/myPage/myPoint")
	public void PointPage(Model model, HttpSession session, int pageNUM) {
		String id = (String) session.getAttribute("id");
		
		totalRecord = mp_dao.pointTotalRecord();
		totalPage = (int)Math.ceil(totalRecord/(double)pageSIZE);
		
		int start = (pageNUM-1)*pageSIZE+1;
		int end = start + pageSIZE - 1;
		
		List<EventVO> list = null;
		int memberno = m_dao.findById(id).getMemberno();
		list = mp_dao.findMyPoint(memberno,start,end);
		int point = m_dao.findById(id).getPoint();
		
		for(EventVO e : list) {
			e.setEventdate(e.getEventdate().split(" ")[0]);
		}
		
		
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("id", id);
		model.addAttribute("point", point);
		model.addAttribute("list", list);
		
	}
	
	@GetMapping("/myPage/roulette")
	public void Roulette(Model model,HttpSession session) {
		String id = (String) session.getAttribute("id");
		model.addAttribute("id", id);
	}
	
	@GetMapping("/myPage/myReservation")
	public void ReservationPage(Model model, HttpSession session, int pageNUM) {
		String id = (String) session.getAttribute("id");
		
		totalRecord = mp_dao.reservTotalRecord();
		totalPage = (int)Math.ceil(totalRecord/(double)pageSIZE);
		
		int start = (pageNUM-1)*pageSIZE+1;
		int end = start + pageSIZE - 1;
		
		
		List<ReservationVO> list = null;
		int memberno = m_dao.findById(id).getMemberno();
		list = mp_dao.findMyReserv(memberno,start,end);
		
		for(ReservationVO r : list) {
			String name = mp_dao.findAcc(r.getAccommoNo()).getName();
			r.setName(name);
		}
		
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list);
		
	}
	
	@GetMapping("/myPage/reviewInsert")
	public void insertReview(Model model,HttpSession session, int accommoNo) {
		String id = (String) session.getAttribute("id");
		model.addAttribute("id", id);
		model.addAttribute("accommoNo", accommoNo);
	}
	
	@PostMapping("/myPage/reviewInsert")
	public ModelAndView insertReview(ReviewVO r, HttpSession session) {
		String id = (String) session.getAttribute("id");
		ModelAndView mav = new ModelAndView("redirect:/myPage/myReservation?pageNUM=1");
		int memberno = m_dao.findById(id).getMemberno();
		r.setMemberNo(memberno);
		
		mp_dao.insertReview(r);
		return mav;
	}
	
	@RequestMapping("/myPage/review_chk")
	@ResponseBody
	public int findReview(int accommono, HttpSession session) {
		String id = (String) session.getAttribute("id");
		int re = 0;
		int memberno = m_dao.findById(id).getMemberno();
		re = mp_dao.findReview(accommono, memberno);
		return re;
	}
	
	@GetMapping("/myPage/myLike")
	public void LikePage(Model model, HttpSession session, String category, int pageNUM) {
		String id = (String) session.getAttribute("id");
		totalRecord = mp_dao.likeTotalRecord();
		totalPage = (int)Math.ceil(totalRecord/(double)pageSIZE);
		
		int start = (pageNUM-1)*pageSIZE+1;
		int end = start + pageSIZE - 1;
		
		
		List<LikeVO> list = null;
		int memberno = m_dao.findById(id).getMemberno();
		list = (ArrayList<LikeVO>) mp_dao.findMyLike(memberno, category, start, end);
		
		ArrayList<MyLikeVO> mylist = new ArrayList<MyLikeVO>();
		
		if(category == "accmmodation") {
			for(LikeVO l : list) {
				MyLikeVO m = new MyLikeVO();
				AccommodationVO acc =  mp_dao.findAcc(l.getRefno());
				m.setName(acc.getName());
				m.setPhotopath(mp_dao.findaccphoto(acc.getAccommoNo()));
				m.setLink("/accommo/detail?accommoNo="+l.getRefno());
				mylist.add(m);
				
			}
		}else if(category == "attraction") {
			for(LikeVO l : list) {
				MyLikeVO m = new MyLikeVO();
				AccommodationVO acc =  mp_dao.findAcc(l.getRefno());
				m.setName(acc.getName());
				m.setPhotopath(mp_dao.findaccphoto(acc.getAccommoNo()));
				m.setLink("/accommo/detail?accommoNo="+l.getRefno());
				mylist.add(m);
				
			}
		}else if(category == "restaurant") {
			for(LikeVO l : list) {
				MyLikeVO m = new MyLikeVO();
				AccommodationVO acc =  mp_dao.findAcc(l.getRefno());
				m.setName(acc.getName());
				m.setPhotopath(mp_dao.findaccphoto(acc.getAccommoNo()));
				m.setLink("/accommo/detail?accommoNo="+l.getRefno());
				mylist.add(m);
				
			}
		}else {
			for(LikeVO l : list) {
				MyLikeVO m = new MyLikeVO();
				AccommodationVO acc =  mp_dao.findAcc(l.getRefno());
				m.setName(acc.getName());
				m.setPhotopath(mp_dao.findaccphoto(acc.getAccommoNo()));
				m.setLink("/accommo/detail?accommoNo="+l.getRefno());
				mylist.add(m);
				
			}
		}
		
		model.addAttribute("category", category);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", mylist);
		
	}
	
	@RequestMapping("/myPage/roulette_chk")
	@ResponseBody
	public int roulette_chk(HttpSession session) {
		String id = (String) session.getAttribute("id");
		int re = 0;
		MemberVO m = m_dao.findById(id);
		re = m.getRoulettecount();
		return re;
	}
	
	@RequestMapping("/myPage/roulette_winning")
	@ResponseBody
	public int roulette_winning(HttpSession session, int point) {
		String id = (String) session.getAttribute("id");
		int re = 0;
		int memberno = m_dao.findById(id).getMemberno();
		if(point != 0) {
		re = mp_dao.roulette_count(memberno);
		re = mp_dao.point_update(memberno, point);
		re = mp_dao.point_insert(memberno, point);
		System.out.println(re);
		}
		return re;
	}
	
	@GetMapping("/myPage/myInquiry")
	public void findMyInquiry(Model model, HttpSession session,int pageNUM) {
		String id = (String) session.getAttribute("id");
		totalRecord = mp_dao.inquiryTotalRecord();
		totalPage = (int)Math.ceil(totalRecord/(double)pageSIZE);
		
		int start = (pageNUM-1)*pageSIZE+1;
		int end = start + pageSIZE - 1;
		
		List<InquiryVO> list = null;
		int memberno = m_dao.findById(id).getMemberno();
		list = mp_dao.findMyInquiry(memberno,start,end);
		
		for(InquiryVO l: list) {
			if(mp_dao.findMyReply(l.getInquiryNo()) !=null) {
				l.setReplyOk(1);
			}else {
				l.setReplyOk(0);
			}
		}
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list);
		
	}
	
	@GetMapping("/myPage/inquiryDetail")
	public void detailInquiry(Model model, int inquiryno) {
		InquiryVO i = null;
		ReplyVO r = null;
		i = mp_dao.findMyInquiryByNo(inquiryno);
		r = mp_dao.findMyReply(inquiryno);
		
		model.addAttribute("inquiryno", inquiryno);
		
		model.addAttribute("i_title", i.getTitle());
		model.addAttribute("i_inqdate", i.getInqdate());
		model.addAttribute("i_content", i.getContent());
		model.addAttribute("replyOk", i.getReplyOk());
		
		if(r == null) {
			model.addAttribute("r_repdate", "");
			model.addAttribute("r_content", "아직 답변이 달리지 않은 문의입니다.");
		}else {
			model.addAttribute("r_repdate", r.getRepdate());
			model.addAttribute("r_content", r.getContent());
		}
	}
	
	@GetMapping("/myPage/updateInquiry")
	public void updateInquiryForm(Model model, int inquiryno) {
		InquiryVO i = null;
		i = mp_dao.findMyInquiryByNo(inquiryno);
		
		model.addAttribute("i", i);
		
	
	}
	
	@PostMapping("/myPage/updateInquiry")
	public ModelAndView updateInquirySubmit(InquiryVO i) {
		ModelAndView mav = new ModelAndView("redirect:/myPage/inquiryDetail?inquiryno="+i.getInquiryNo());
		int re = -1;
		re = mp_dao.updateInquiry(i);
		
		return mav;
	
	}
	
	@GetMapping("/myPage/deleteInquiry")
	public ModelAndView deleteInquiry(int inquiryno, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/myPage/myInquiry?id="+session.getAttribute("id"));
		int re = -1;
		re = mp_dao.deleteInquiry(inquiryno);
		
		return mav;
	}
	
	
	
	
	
}
