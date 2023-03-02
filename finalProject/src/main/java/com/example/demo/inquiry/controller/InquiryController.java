package com.example.demo.inquiry.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.inquiry.dao.InquiryDAO;
import com.example.demo.inquiry.vo.InquiryVO;
import com.example.demo.member.dao.UserMemberDAO;

import lombok.Setter;

@Controller
@Setter
public class InquiryController {
	
	@Autowired
	InquiryDAO inquiryDao;
	
	@Autowired
	UserMemberDAO mdao;
	
	@GetMapping("/main/inquiry")
	public ModelAndView inquiryForm() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@PostMapping("/main/inquiry")
	public ModelAndView inquiryForm2(InquiryVO vo, HttpSession session) {
		vo.setInquiryNo(inquiryDao.getNextNo());
		vo.setMemberNo(mdao.findById((String)session.getAttribute("id")).getMemberno());
//		String content=vo.getContent();		
		int re=inquiryDao.insertInquiry(vo);
		
			
		ModelAndView mav = new ModelAndView("redirect:/");
			
		return mav;
	}
	
}
