package com.example.demo.controller.accommodation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.accommodation.AccommoDAO;
import com.example.demo.vo.accommodation.AccommoPhotoVO;
import com.example.demo.vo.accommodation.AccommodationVO;
import com.example.demo.vo.accommodation.LikeVO;

import lombok.Setter;

@Controller
@RequestMapping("/accommo")
@Setter
public class AccommoController {
	
	@Autowired
	private AccommoDAO dao;

	@GetMapping("/main")
	public String accommoMain(Model model) {
		// 찜 많은 숙소
		List<LikeVO> like_list = dao.findMostLike(5);
		LikeVO l = new LikeVO();
		List<AccommodationVO> accommo_list = new ArrayList<>();
		for(int i=0;i<like_list.size();i++){
			l = like_list.get(i);
			int refNo = l.getRefNo();
			accommo_list.add(dao.findById(refNo));
//			System.out.println(i+": "+refNo);
		}
//		System.out.println(accommo_list);
		
		model.addAttribute("accommo_list", accommo_list);
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
	
	@GetMapping("/findById")
	public String findById(Model model) {
		List<AccommodationVO> list = dao.findAllPhotoById(148);
		AccommodationVO a = new AccommodationVO();
		String realPath = "";
		String category = "";
		String name = "";
		String path = "";
		if(list.size() > 0) {
//			System.out.println(list);
			for(int i=0;i<list.size();i++) {
				//대표 이미지
				a = list.get(0);
				category = a.getCategory();
				name = a.getName();
				path = a.getPath();
				realPath = "photo/Accommodation/"+category+"/"+name+"/"+path;
//				System.out.println(realPath);
//				System.out.println("next");
			}
		}else {
			System.out.println("이미지 없음");
		}
//		System.out.println(realPath);
		model.addAttribute("realPath",realPath);
		return "Accommodation/Main";
	}
	
	// 찜 많은 숙소 찾기
	@GetMapping("/findMostLike")
	public String findMostLike() {
		List<LikeVO> list = dao.findMostLike(5);
		LikeVO l = new LikeVO();
		for(int i=0;i<list.size();i++){
			l = list.get(i);
			int refNo = l.getRefNo();
			System.out.println(i+": "+refNo);
		}
		return "Accommodation/Main";
	}
}
