package com.example.demo.accommodation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.accommodation.dao.AccommoDAO;
import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.member.dao.MemberDAOLHH;
import com.example.demo.member.vo.MemberVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/accommo")
@Setter
public class AdminAccomoController {

	@Autowired
	private AccommoDAO dao;
	
	@Autowired
	private MemberDAOLHH mdao;
	
	@GetMapping("/update/{accommoNo}")
	public ModelAndView updateForm(@PathVariable int accommoNo, HttpSession session) {
		ModelAndView mav = new ModelAndView("Admin/Accommodation/Update");
		AccommodationVO a = dao.findById(accommoNo);
		mav.addObject("a", a);
		MemberVO m = mdao.findByNo(1);
		session.setAttribute("loginM", m);
		return mav;
	}
	
	@PostMapping("/updateSubmit")
	public ModelAndView updateSubmit(AccommodationVO a) {
		ModelAndView mav = new ModelAndView();
		int accommoNo = a.getAccommoNo();
		mav.setViewName("redirect:/admin/accommo/update/"+accommoNo);
		System.out.println(a);
		// update 문
		int re = dao.updateById(a);
		if(re > 0) {
			mav.addObject("update","yes");
		}else {
			mav.addObject("update","no");
		}
		return mav;
	}
	
	@GetMapping("/delete/{accommoNo}")
	public ModelAndView delete(@PathVariable int accommoNo) {
		ModelAndView mav = new ModelAndView("redirect:/admin/accommo/update/"+accommoNo);
		System.out.println(accommoNo);
//		int re = dao.deleteById(accommoNo);
		return mav;
	}
}
