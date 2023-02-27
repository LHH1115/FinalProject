package com.example.demo.restaurant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



import com.example.demo.member.dao.MemberDAO;
import com.example.demo.member.vo.MemberVO;
import com.example.demo.restaurant.dao.RestaurantDAO;
import com.example.demo.restaurant.vo.LikeVO;
import com.example.demo.restaurant.vo.RestaurantPhotoVO;
import com.example.demo.restaurant.vo.RestaurantVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Setter;

@Controller
@RequestMapping("/restau")
@Setter
public class RestaurantController {
	
	public int totPage = 1;		// 페이징 처리 위한 변수
	public int totCnt = 0;		// 페이징 처리 위한 변수
	public int pageSize = 6;	// 페이징 처리 위한 변수(검색 시 한페이지당 보여줄 결과 갯수)
	public int pageGroup = 10;	// 페이징 처리 위한 변수(페이징 번호 그룹 갯수)
	
	@Autowired
	private RestaurantDAO dao;
	
	@Autowired
	private MemberDAO mdao;

	@GetMapping("/main")
	public String restauMain(Model model, HttpSession session) {
		session.removeAttribute("keyword");		// 페이징 처리 세션 제거
		session.removeAttribute("category");	// 페이징 처리 세션 제거
		
		// 인기숙소
		List<LikeVO> like_list = dao.findMostLike(5);	// Top 5 나열
		LikeVO l = new LikeVO();
		List<RestaurantVO> restau_list = new ArrayList<>();
		RestaurantVO a = new RestaurantVO();
		for(int i=0;i<like_list.size();i++){
			l = like_list.get(i);
			int refNo = l.getRefNo();
			a = dao.findById(refNo);
			
			List<RestaurantVO> photo_list = dao.findAllPhotoById(refNo);
			String realPath = "";
			String category = "";
			String name = "";
			String path = "";
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					// 대표 이미지
					RestaurantVO forPhoto = new RestaurantVO();
					forPhoto = photo_list.get(0);
					category = a.getCategory();
					name = forPhoto.getName();
					path = forPhoto.getPath();
					realPath = "photo/Restaurant/"+name+"/"+path;
					a.setRealPath(realPath);
				}
			}else {
				realPath = "photo/Restaurant/노리매/att1.jpeg";
				a.setRealPath(realPath);
			}
			restau_list.add(a);
		}
		// 관리자 여부 확인
		int role = 1;
		session.setAttribute("role", role);
		model.addAttribute("Restau_list", restau_list);
		return "Restaurant/Main";
	}
	
	// 키워드 버튼 누를때
	@GetMapping("/searchC")
	public ModelAndView searchC(String keyword, int pageNum, HttpSession session) {
//		ModelAndView mav = new ModelAndView("Restaurant/Search");
		ModelAndView mav = new ModelAndView("Restaurant/Search");	// 사이드바 테스트 버전
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
		List<RestaurantVO> list = dao.findByAny(map);
		System.out.println(list);
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
			ModelAndView mav = new ModelAndView("Restaurant/Search");
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
			List<RestaurantVO> list = dao.findByAny(map);
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
		RestaurantPhotoVO a = new RestaurantPhotoVO();
		a.setRestauNo(999);
		int re = dao.findPCnt(a);
		System.out.println(re);
		return "Restaurant/Main";
	}
	
	// 사진 리스트 검색
	@GetMapping("/findById")
	public String findById(Model model) {
		List<RestaurantVO> list = dao.findAllPhotoById(148);
		RestaurantVO a = new RestaurantVO();
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
				realPath = "photo/Restaurant/"+category+"/"+name+"/"+path;
//				System.out.println(realPath);
//				System.out.println("next");
			}
		}else {
			System.out.println("이미지 없음");
		}
//		System.out.println(realPath);
		model.addAttribute("realPath",realPath);
		return "Restaurant/Main";
	}
	
	// 찜 많은 관광지 찾기
	@GetMapping("/findMostLike")
	public String findMostLike() {
		List<LikeVO> list = dao.findMostLike(5);
		LikeVO l = new LikeVO();
		for(int i=0;i<list.size();i++){
			l = list.get(i);
			int refNo = l.getRefNo();
			System.out.println(i+": "+refNo);
		}
		return "Restaurant/Main";
	}
	
	// 식당 상세페이지
	@GetMapping("/detail")
	public ModelAndView detail(int restauNo) {
		ModelAndView mav = new ModelAndView("Restaurant/Detail");
		
		// 로그인한 멤버
//		MemberVO m = mdao.findByNo(7);
//		System.out.println(m);
		
		RestaurantVO a = dao.findById(restauNo);
		
		List<RestaurantVO> list = dao.findAllPhotoById(restauNo);
		List<String> photoList = new ArrayList<>();
		String realPath = "";
		String category = a.getCategory();
		String name = "";
		String path = "";
		if(list.size() > 0) {
			for(int i=0;i<list.size();i++) {
				a = list.get(i);
				name = a.getName();
				path = a.getPath();
				realPath = "photo/Restaurant/"+category+"/"+name+"/"+path;
				photoList.add(realPath);
			}
		}else {
			// 이미지 없을때 랜덤이미지
			Random rand = new Random();
			String koreanList[] = {"명가천지연무태장어", "제주광해애월점", "제주반딧불한담", "큰맘할매순대국", "푸른밤의해안속초식당"};
			String westernList[] = {"루마카", "반양", "카우보이스테이크하우스"};
			String japaneseList[] = {"스시앤", "아일랜드본섬", "해모둠", "해원앙", "혼참치"};
//			String hostelList[] = {"길리 리조트(구.협재 사계절 리조트)", "라이트프리(구. 에바다호스텔)", "아마스빌 리조트(구.아마스빌 호스텔)", "용두암캐빈", "해미안"};
//			String condoList[] = {"메가리조트제주", "사조그랜드리조트", "이랜드파크 켄싱턴리조트 제주한림점", "일성제주콘도미니엄", "제주토비스콘도①"};
				switch (category) {
					case "한식":{
						String k = koreanList[rand.nextInt(5)];
						for(int i=0;i<5;i++) {
							realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
							photoList.add(realPath);
						}
					}break;
					case "서양식":{
						String k = westernList[rand.nextInt(3)];
						for(int i=0;i<5;i++) {
						realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
						photoList.add(realPath);
						}
					}break;
					case "일식":{
						String k = japaneseList[rand.nextInt(4)];
						for(int i=0;i<5;i++) {
						realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
						photoList.add(realPath);
						}
					}break;
//					case "호스텔업":{
//						for(int i=0;i<5;i++) {
//						realPath = "photo/Restaurant/"+category+"/"+hostelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
//						photoList.add(realPath);
//						}
//					}break;
//					case "휴양콘도미니엄업":{
//						for(int i=0;i<5;i++) {
//						realPath = "photo/Restaurant/"+category+"/"+condoList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
//						photoList.add(realPath);
//						}
//					}break;
				}
			
//			System.out.println("대체 이미지: "+photoList);
		}
//		System.out.println(photoList);
//		mav.addObject("m", m);
		mav.addObject("a", a);
		mav.addObject("photoList", photoList);
		return mav;
	}
	
	
}
