package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.MemberDAO;
import com.example.demo.dao.MyPageDAO;
import com.example.demo.vo.MemberVO;

@Controller
public class MyPageController {
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
	public void updateMyInfoForm(Model model,String id) {
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
	
	
	
}
