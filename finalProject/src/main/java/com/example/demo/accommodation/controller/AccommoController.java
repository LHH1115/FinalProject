package com.example.demo.accommodation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.accommodation.dao.AccommoDAO;
import com.example.demo.accommodation.vo.AccommoPhotoVO;
import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.accommodation.vo.LikeVO;

import lombok.Setter;

@Controller
@RequestMapping("/accommo")
@Setter
public class AccommoController {
	
	public int totPage = 1;		// 페이징 처리 위한 변수
	public int totCnt = 0;		// 페이징 처리 위한 변수
	public int pageSize = 50;	// 페이징 처리 위한 변수(검색 시 한페이지당 보여줄 결과 갯수)
	public int pageGroup = 10;	// 페이징 처리 위한 변수(페이징 번호 그룹 갯수)
	
	@Autowired
	private AccommoDAO dao;

	@GetMapping("/main")
	public String accommoMain(Model model, HttpSession session) {
		session.removeAttribute("keyword");		// 페이징 처리 세션 제거
		session.removeAttribute("category");	// 페이징 처리 세션 제거
		
		// 인기숙소
		List<LikeVO> like_list = dao.findMostLike(5);	// Top 5 나열
		LikeVO l = new LikeVO();
		List<AccommodationVO> accommo_list = new ArrayList<>();
		AccommodationVO a = new AccommodationVO();
		for(int i=0;i<like_list.size();i++){
			l = like_list.get(i);
			int refNo = l.getRefNo();
			a = dao.findById(refNo);
			
			List<AccommodationVO> photo_list = dao.findAllPhotoById(refNo);
			String realPath = "";
			String category = "";
			String name = "";
			String path = "";
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					// 대표 이미지
					AccommodationVO forPhoto = new AccommodationVO();
					forPhoto = photo_list.get(0);
					category = a.getCategory();
					name = forPhoto.getName();
					path = forPhoto.getPath();
					realPath = "photo/Accommodation/"+category+"/"+name+"/"+path;
					a.setRealPath(realPath);
				}
			}else {
				realPath = "photo/Accommodation/가족호텔업/그림리조트/acc1.jpeg";
				a.setRealPath(realPath);
			}
			accommo_list.add(a);
		}
		// 관리자 여부 확인
		int role = 1;
		session.setAttribute("role", role);
		model.addAttribute("accommo_list", accommo_list);
		return "Accommodation/Main";
	}
	
	// 키워드 버튼 누를때
	@GetMapping("/searchC")
	public ModelAndView searchC(String keyword, int pageNum, HttpSession session) {
//		ModelAndView mav = new ModelAndView("Accommodation/Search");
		ModelAndView mav = new ModelAndView("Accommodation/SearchTest");
//		System.out.println("keyword:"+keyword);
//		System.out.println("category:"+category);
		
		if(session.getAttribute("keyword") != null) {
			keyword = (String) session.getAttribute("keyword");
		}
		
		int start = 1;
		int end = 1;
		if(pageNum == 1) {
			start = 1;
			end = start + pageSize - 1;
		}else {
			start = (pageNum-1)*pageSize+1;
			end = start + pageSize - 1;
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		List<AccommodationVO> list = dao.findByAny(map);
		totCnt = dao.findCountByAny(keyword);
		totPage = (int) Math.ceil(totCnt/pageSize);
		int startPage = (pageNum-1)/pageGroup*pageGroup+1;
		int endPage = startPage+pageGroup-1;
		if(totPage < endPage) {
			endPage = totPage;
		}
		session.setAttribute("keyword", keyword);
		mav.addObject("totPage", totPage);
		mav.addObject("startPage", startPage);
		mav.addObject("endPage", endPage);
		mav.addObject("list",list);
		return mav;
	}
	
	// 키워드 검색
		@GetMapping("/main/search")
		public ModelAndView search(String keyword, String category, int pageNum, HttpSession session) {
			ModelAndView mav = new ModelAndView("Accommodation/Search");
//			System.out.println("keyword:"+keyword);
//			System.out.println("category:"+category);
			
			if(session.getAttribute("keyword") != null) {
				keyword = (String) session.getAttribute("keyword");
			}
			if(session.getAttribute("category") != null) {
				category = (String) session.getAttribute("category");
			}
			
			int start = 1;
			int end = 1;
			if(pageNum == 1) {
				start = 1;
				end = start + pageSize - 1;
			}else {
				start = (pageNum-1)*pageSize+1;
				end = start + pageSize - 1;
			}
			HashMap<String, Object> map = new HashMap<>();
			map.put("keyword", keyword);
			map.put("start", start);
			map.put("end", end);
			List<AccommodationVO> list = dao.findByAny(map);
			totCnt = dao.findCountByAny(keyword);
			totPage = (int) Math.ceil(totCnt/pageSize);
			int startPage = (pageNum-1)/pageGroup*pageGroup+1;
			int endPage = startPage+pageGroup-1;
			if(totPage < endPage) {
				endPage = totPage;
			}
			session.setAttribute("keyword", keyword);
			session.setAttribute("category", category);
			mav.addObject("totPage", totPage);
			mav.addObject("startPage", startPage);
			mav.addObject("endPage", endPage);
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
	
	// 사진 리스트 검색
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
