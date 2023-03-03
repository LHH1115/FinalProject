package com.example.demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.test.dao.BoardDAO;

@Controller
public class BoardController {
	
	
	@Autowired
	private BoardDAO dao;	
	
	@GetMapping("/test")
	public String list() {
		return "test";
	}
	
}



















