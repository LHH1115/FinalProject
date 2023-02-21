package com.example.demo.accommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.accommodation.dao.AccommoDAO;
import com.example.demo.member.dao.MemberDAO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/accommo")
@Setter
public class AdminAccomoController {

	@Autowired
	private AccommoDAO dao;
	
	@Autowired
	private MemberDAO mdao;
	
	@GetMapping("/update/{accommoNo}")
	public ModelAndView updateForm(@PathVariable int accommoNo) {
		ModelAndView mav = new ModelAndView("/Admin/Accommodation/Update");
		return mav;
	}
}
