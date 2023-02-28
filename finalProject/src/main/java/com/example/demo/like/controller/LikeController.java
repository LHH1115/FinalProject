package com.example.demo.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.like.dao.LikeDAO;
import com.example.demo.like.vo.LikeVO;

import lombok.Setter;

@Controller
@Setter
public class LikeController {
	
	@Autowired
	LikeDAO likedao;
	
	@GetMapping("/rentcar/like")
	public ModelAndView doLike(int carNo) {
		
		ModelAndView mav = new ModelAndView("redirect:/rentcar/main");
		
		LikeVO vo = new LikeVO();
		vo.setRefNo(carNo);
		int likeno=likedao.getMaxNo();
		vo.setLikeNo(likeno);
		vo.setCategory("RentCar");
		vo.setMemberNo(4);
		likedao.doLike(vo);
		return mav;
	}
	
	@GetMapping("/rentcar/unlike")
	public ModelAndView unlike(int carNo) {
		ModelAndView mav = new ModelAndView("redirect:/rentcar/main");
		LikeVO vo = new LikeVO();
		vo.setCategory("RentCar");
		vo.setMemberNo(4);
		vo.setRefNo(carNo);
		likedao.unlike(vo);
		return mav;
	}
	
	
}
