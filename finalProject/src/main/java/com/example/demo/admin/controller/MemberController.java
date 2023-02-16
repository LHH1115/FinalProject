package com.example.demo.admin.controller;


import java.awt.print.Pageable;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.admin.dao.MemberDAO;

@Controller
public class MemberController {

	@Autowired
	private MemberDAO memberDAO;
	
	public int pageSize = 4;
	public int totalRecord = 0;
	public int totalPage = 1;
	
	public int pageGROUP  = 5;
	
	// 관리자 회원 조회
	@GetMapping("admin/memberManage")
	public String memberList(Model model, @RequestParam(value = "pageNUM",defaultValue = "1") int pageNUM) {
		totalRecord = memberDAO.getTotalRecord();
		totalPage = (int) Math.ceil( totalRecord / (double)pageSize);
		
		int start = (pageNUM-1) * pageSize + 1;
		int end = start + pageSize -1;
		HashMap<String , Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		System.out.println(totalPage);
		model.addAttribute("total",totalPage);
		model.addAttribute("memberList",memberDAO.findAll(map));
		
		int startPage = (pageNUM-1)/pageGROUP*pageGROUP+1;
		int endPage = startPage+pageGROUP-1;
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "admin/memberManage";
	}
	
	
	@GetMapping("/admin/inquiryManage")
	public String inquiryList() {
		return "admin/inquiryManage";
	}
	
	@GetMapping("/admin/productManage")
	public String productList() {
		return "admin/productManage";
	}
	
	
	// 메인 페이지
	@GetMapping("/")
	public String main() {
		return "main/mainPage";
	}


}
