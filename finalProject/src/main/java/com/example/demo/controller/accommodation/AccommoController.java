package com.example.demo.controller.accommodation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.accommodation.AccommoDAO;
import com.example.demo.vo.accommodation.AccommoPhotoVO;
import com.example.demo.vo.accommodation.AccommodationVO;

import lombok.Setter;

@Controller
@RequestMapping("/accommo")
@Setter
public class AccommoController {
	
	@Autowired
	private AccommoDAO dao;

	@GetMapping("/main")
	public String accommoMain() {
		return "Accommodation/Main";
	}
	
	@GetMapping("/search")
	public ModelAndView search(String keyword, String category) {
		ModelAndView mav = new ModelAndView("Accommodation/Search");
		System.out.println("keyword:"+keyword);
		System.out.println("category:"+category);
		List<AccommodationVO> list = dao.findByCategory(keyword);
		mav.addObject("list",list);
		return mav;
	}
	
	// 사진 보유 여부 검색
	// 없으면 랜덤이미지 사용
	@GetMapping("/cnt")
	public String cntP() {
		AccommoPhotoVO a = new AccommoPhotoVO();
		a.setAccommoNo(999);
		int re = dao.findPCnt(a);
		System.out.println(re);
		return "Accommodation/Main";
	}
}
