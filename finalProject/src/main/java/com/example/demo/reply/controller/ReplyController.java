package com.example.demo.reply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.inquiry.dao.InquiryDAO;
import com.example.demo.inquiry.vo.InquiryVO;
import com.example.demo.reply.dao.ReplyDAO;
import com.example.demo.reply.vo.ReplyVO;

import lombok.Setter;

@Controller
@Setter
public class ReplyController {
	
	@Autowired
	InquiryDAO inquiryDAO;
	
	@Autowired
	ReplyDAO replyDAO;
	
	@GetMapping("/admin/replyManage")	
	public Model answerReply(Model model,
						int inquiryNo
	) {
		InquiryVO ivo=inquiryDAO.findByInquiryNo(inquiryNo);
		int re=replyDAO.countAll(inquiryNo);
		ReplyVO rvo=replyDAO.findByInquiryNo(inquiryNo);
		System.out.println(ivo);
		System.out.println(rvo);
		
		model.addAttribute("inquiryVo", ivo);
		model.addAttribute("countReply",re);
		model.addAttribute("replyVo",rvo);
		return model;
	}
	
	@PostMapping("/admin/replyManage")
	public ModelAndView answerReply(ReplyVO vo) {
		System.out.println("여기까지");
		ModelAndView mav = new ModelAndView("redirect:/admin/inquiryManage");
		int no=replyDAO.getNextNo();
		vo.setReplyNo(no);	
		replyDAO.insertReply(vo);
		return mav;
	}
	
	@PostMapping("/admin/replyUpdate")
	public ModelAndView updateReply(ReplyVO vo) {
		ModelAndView mav = new ModelAndView("redirect:/admin/inquiryManage");
		replyDAO.updateReply(vo);
		return mav;
	}
	
	@GetMapping("/admin/replyDelete")
	public ModelAndView deleteReply(int no) {
		System.out.println("dd");
		ModelAndView mav = new ModelAndView("redirect:/admin/inquiryManage");
		replyDAO.deleteReply(no);
		return mav;
	}
	
	
	
	
}
