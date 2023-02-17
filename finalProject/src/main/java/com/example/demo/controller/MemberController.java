package com.example.demo.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.MemberDAO;
import com.example.demo.vo.MemberVO;

@Controller
public class MemberController {
	@Autowired
	private MemberDAO dao;

	public void setDao(MemberDAO dao) {
		this.dao = dao;
	}
	
	@GetMapping("/member/insertMember")
	public void insertForm() {
	}
	
	@PostMapping("/member/insertMember")
	public ModelAndView insertSubmit(MemberVO m,int jumin_f, int jumin_b, String addr_f, String addr_b) {
		ModelAndView mav = new ModelAndView("redirect:/");
		m.setJumin(jumin_f+"-"+jumin_b);
		m.setAddr(addr_f+" "+addr_b);

		int re = dao.insertMember(m);
		System.out.println(re);
		
		return mav;
	}
	
	@RequestMapping("/member/dup_chk")
	@ResponseBody
	public int dub_chk(HttpServletRequest request) {
		int re = 0;
		String id = request.getParameter("id");
		re = dao.chk_id(id);
		return re;
	}
	
	
}
