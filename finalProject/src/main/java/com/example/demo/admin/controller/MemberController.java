package com.example.demo.admin.controller;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.admin.dao.MemberDAO;
import com.example.demo.admin.vo.ReservationVO;
import com.example.demo.admin.vo.ReviewVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MemberController {

	@Autowired
	private MemberDAO memberDAO;
	
	public int pageSize = 4;
	public int totalRecord = 0;
	public int totalPage = 1;
	public int pageGROUP  = 3;
	
	// 관리자 회원조회 페이지
	@GetMapping("admin/memberManage")
	public String memberList(Model model, @RequestParam(value = "pageNUM",defaultValue = "1") int pageNUM
			 , String searchColumn, String keyword, HttpSession session, String reset) {
		
		String session_column = null;

		if(session.getAttribute("session_column") != null) {
			session_column = (String)session.getAttribute("session_column");
		}
		
		if(searchColumn != null && !searchColumn.equals("")) {
			session_column = searchColumn;
		}
		
		if(session.getAttribute("keyword") != null &&( keyword == null || keyword.equals("") )) {
			keyword = (String)session.getAttribute("keyword");
		}
		
		HashMap<String , Object> map = new HashMap<String, Object>();
		HashMap<String , Object> map2 = new HashMap<String, Object>();
		
		map2.put("keyword", keyword);
		map2.put("column", session_column);
		
		totalRecord = memberDAO.getTotalRecord(map2);
		totalPage = (int) Math.ceil( totalRecord / (double)pageSize);
		int start = (pageNUM-1) * pageSize + 1;
		int end = start + pageSize -1;
		int startPage = (pageNUM-1)/pageGROUP*pageGROUP+1;
		int endPage = startPage+pageGROUP-1;
		
		// 1 
		
		map.put("start", start);
		map.put("end", end);
		map.put("keyword", keyword);
		map.put("column", session_column);

		System.out.println("keyowrd : "+keyword);
		System.out.println("searchColumn : "+searchColumn);
		System.out.println("startPage : "+startPage);
		System.out.println("endPage : "+endPage);
		System.out.println("totalRecord : "+totalRecord);
		System.out.println("totalPage : "+totalPage);
		
		session.setAttribute("session_column", session_column);
		session.setAttribute("keyword", keyword);
		
		model.addAttribute("total",totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("memberList",memberDAO.findAll(map));
		
		return "admin/memberManage";
	}
	
	// 각 회원마다 리뷰 조회 
	@GetMapping("/admin/memberReviewList")
	@ResponseBody
	public String memberReviewList(HttpServletRequest request) {
		int memberNo = Integer.parseInt(request.getParameter("memberNo"));
		List<ReviewVO> reviewVO = memberDAO.memberReviewList(memberNo);
		String jsonString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonString = mapper.writeValueAsString(reviewVO);
		} catch (JsonProcessingException e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		System.out.println("각회원 리뷰 리스트 : "+jsonString);
		return jsonString;
	}
	
	// 각 회원마다 예약 조회 
	@GetMapping("/admin/memberReservationList")
	@ResponseBody
	public String memberResrvationList(HttpServletRequest request) {
		int memberNo = Integer.parseInt(request.getParameter("memberNo"));
		List<ReservationVO> reservationVO = memberDAO.memberReservationList(memberNo);
		String jsonString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonString = mapper.writeValueAsString(reservationVO);
		} catch (JsonProcessingException e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		System.out.println("각회원 예약 리스트 : "+jsonString);
		return jsonString;
	}
	
		
	// 메인 페이지
		@GetMapping("/")
		public ModelAndView main(HttpSession session) {
			ModelAndView mav = new ModelAndView("main/mainPage.html");	
			return mav;
		}


}
