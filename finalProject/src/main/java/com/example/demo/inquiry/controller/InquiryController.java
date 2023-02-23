package com.example.demo.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.inquiry.dao.InquiryDAO;
import com.example.demo.inquiry.vo.InquiryVO;

import lombok.Setter;

@Controller
@Setter
public class InquiryController {
	
	@Autowired
	InquiryDAO inquiryDao;
	
	@GetMapping("/main/inquiry")
	public ModelAndView inquiryForm() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@PostMapping("/main/inquiry")
	public ModelAndView inquiryForm2(InquiryVO vo) {
		
		vo.setInquiryNo(inquiryDao.getNextNo());
//		String content=vo.getContent();		
		int re=inquiryDao.insertInquiry(vo);
	
			
		ModelAndView mav = new ModelAndView("main/mainPage.html");
			
		return mav;
	}
	
}
