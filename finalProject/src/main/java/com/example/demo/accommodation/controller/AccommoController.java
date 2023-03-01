package com.example.demo.accommodation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.accommodation.dao.AccommoDAO;
import com.example.demo.accommodation.vo.AccommoPhotoVO;
import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.accommodation.vo.LikeVO;
import com.example.demo.accommodation.vo.PhotoListVO;
import com.example.demo.accommodation.vo.ReservationVO;
import com.example.demo.admin.dao.MemberDAO;
import com.example.demo.admin.vo.MemberVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Setter;

@Controller
@RequestMapping("/accommo")
@Setter
public class AccommoController {
	
	public int totPage = 1;		// 페이징 처리 위한 변수
	public int totCnt = 0;		// 페이징 처리 위한 변수
	public int pageSize = 6;	// 페이징 처리 위한 변수(검색 시 한페이지당 보여줄 결과 갯수)
	public int pageGroup = 10;	// 페이징 처리 위한 변수(페이징 번호 그룹 갯수)
	
	@Autowired
	private AccommoDAO dao;
	
	@Autowired
	private MemberDAO mdao;

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
//		int role = 1;
//		session.setAttribute("role", role);
		model.addAttribute("accommo_list", accommo_list);
		return "Accommodation/Main";
	}
	
	// 키워드 버튼 누를때
	@GetMapping("/searchC")
	public ModelAndView searchC(String keyword, int pageNum, HttpSession session) {
//		ModelAndView mav = new ModelAndView("Accommodation/Search");
		ModelAndView mav = new ModelAndView("Accommodation/Search");	// 사이드바 테스트 버전
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
		
		for(int i =0;i<list.size();i++) {
			int refNo = list.get(i).getAccommoNo();
			List<AccommodationVO> photo_list = dao.findAllPhotoById(refNo);
			String arealPath = "";
			String acategory = list.get(i).getCategory();
			String aname = list.get(i).getName();
			String apath = "";
			
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					AccommodationVO forPhoto = new AccommodationVO();
					forPhoto = photo_list.get(0);
					apath = forPhoto.getPath();
					arealPath = "photo/Accommodation/"+acategory+"/"+aname+"/"+apath;
					list.get(i).setRealPath(arealPath);
				}
			}else {
				Random rand = new Random();
				String fhotellList[] = {"그림리조트", "꼬뜨도르가족호텔", "다인리조트", "베스트웨스턴 제주호텔", "올레리조트"};
				String guestList[] = {"민트게스트하우스", "섬게스트하우스", "슬로시티게스트하우스", "제주공항게스트하우스웨이브사운드", "토다게스트"};
				String thotelList[] = {"(주)호텔하니크라운", "제주썬호텔", "제주팔레스호텔", "글래드호텔앤리조트㈜ 메종글래드제주", "제주로얄호텔"};
				String hostelList[] = {"길리 리조트(구.협재 사계절 리조트)", "라이트프리(구. 에바다호스텔)", "아마스빌 리조트(구.아마스빌 호스텔)", "용두암캐빈", "해미안"};
				String condoList[] = {"메가리조트제주", "사조그랜드리조트", "이랜드파크 켄싱턴리조트 제주한림점", "일성제주콘도미니엄", "제주토비스콘도①"};
					switch (acategory) {
						case "가족호텔업":{
							for(int j=0;j<5;j++) {
								arealPath = "photo/Accommodation/"+acategory+"/"+fhotellList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
								list.get(i).setRealPath(arealPath);
							}
						}break;
						case "게스트하우스":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+guestList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "관광호텔업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+thotelList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "호스텔업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+hostelList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "휴양콘도미니엄업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+condoList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
					}
			}
			
		}
		
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
		if(session.getAttribute("minPrice") != null) {
			session.removeAttribute("minPrice");
		}
		if(session.getAttribute("maxPrice") != null) {
			session.removeAttribute("maxPrice");
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
		
		for(int i =0;i<list.size();i++) {
			int refNo = list.get(i).getAccommoNo();
			List<AccommodationVO> photo_list = dao.findAllPhotoById(refNo);
			String arealPath = "";
			String acategory = list.get(i).getCategory();
			String aname = list.get(i).getName();
			String apath = "";
			
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					AccommodationVO forPhoto = new AccommodationVO();
					forPhoto = photo_list.get(0);
					apath = forPhoto.getPath();
					arealPath = "photo/Accommodation/"+acategory+"/"+aname+"/"+apath;
					list.get(i).setRealPath(arealPath);
				}
			}else {
				Random rand = new Random();
				String fhotellList[] = {"그림리조트", "꼬뜨도르가족호텔", "다인리조트", "베스트웨스턴 제주호텔", "올레리조트"};
				String guestList[] = {"민트게스트하우스", "섬게스트하우스", "슬로시티게스트하우스", "제주공항게스트하우스웨이브사운드", "토다게스트"};
				String thotelList[] = {"(주)호텔하니크라운", "제주썬호텔", "제주팔레스호텔", "글래드호텔앤리조트㈜ 메종글래드제주", "제주로얄호텔"};
				String hostelList[] = {"길리 리조트(구.협재 사계절 리조트)", "라이트프리(구. 에바다호스텔)", "아마스빌 리조트(구.아마스빌 호스텔)", "용두암캐빈", "해미안"};
				String condoList[] = {"메가리조트제주", "사조그랜드리조트", "이랜드파크 켄싱턴리조트 제주한림점", "일성제주콘도미니엄", "제주토비스콘도①"};
					switch (acategory) {
						case "가족호텔업":{
							for(int j=0;j<5;j++) {
								arealPath = "photo/Accommodation/"+acategory+"/"+fhotellList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
								list.get(i).setRealPath(arealPath);
							}
						}break;
						case "게스트하우스":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+guestList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "관광호텔업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+thotelList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "호스텔업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+hostelList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "휴양콘도미니엄업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+condoList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
					}
			}
			
		}
		
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
	
	// 상세 검색
	@GetMapping("/detailSearch")
	public ModelAndView detailSearch(int pageNum, 
			String category, Integer minPrice, Integer maxPrice, HttpSession session) {
		ModelAndView mav = new ModelAndView("Accommodation/Search");
		
		if(session.getAttribute("dscategory") != null) {
			category = (String) session.getAttribute("dscategory");
		}
		if(session.getAttribute("minPrice") != null) {
			minPrice = (Integer) session.getAttribute("minPrice");
		}
		if(session.getAttribute("maxPrice") != null) {
			maxPrice = (Integer) session.getAttribute("maxPrice");
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
		if(category.equals("all")) {
			map.put("dscategory", "");
		}else {
			map.put("dscategory", category);
		}
		
		if(minPrice == null) {
			minPrice = 1;
			map.put("minPrice", 1);
		}else {
			map.put("minPrice", minPrice);
		}
		
		if(maxPrice == null) {
			maxPrice = 9999999;
			map.put("maxPrice", 9999999);
		}else {
			map.put("maxPrice", maxPrice);
		}
		
		map.put("start", start);
		map.put("end", end);
		
		System.out.println(map);
		
		List<AccommodationVO> list = dao.detailSearch(map);
		
		for(int i =0;i<list.size();i++) {
			int refNo = list.get(i).getAccommoNo();
			List<AccommodationVO> photo_list = dao.findAllPhotoById(refNo);
			String arealPath = "";
			String acategory = list.get(i).getCategory();
			String aname = list.get(i).getName();
			String apath = "";
			
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					AccommodationVO forPhoto = new AccommodationVO();
					forPhoto = photo_list.get(0);
					apath = forPhoto.getPath();
					arealPath = "photo/Accommodation/"+acategory+"/"+aname+"/"+apath;
					list.get(i).setRealPath(arealPath);
				}
			}else {
				Random rand = new Random();
				String fhotellList[] = {"그림리조트", "꼬뜨도르가족호텔", "다인리조트", "베스트웨스턴 제주호텔", "올레리조트"};
				String guestList[] = {"민트게스트하우스", "섬게스트하우스", "슬로시티게스트하우스", "제주공항게스트하우스웨이브사운드", "토다게스트"};
				String thotelList[] = {"(주)호텔하니크라운", "제주썬호텔", "제주팔레스호텔", "글래드호텔앤리조트㈜ 메종글래드제주", "제주로얄호텔"};
				String hostelList[] = {"길리 리조트(구.협재 사계절 리조트)", "라이트프리(구. 에바다호스텔)", "아마스빌 리조트(구.아마스빌 호스텔)", "용두암캐빈", "해미안"};
				String condoList[] = {"메가리조트제주", "사조그랜드리조트", "이랜드파크 켄싱턴리조트 제주한림점", "일성제주콘도미니엄", "제주토비스콘도①"};
					switch (acategory) {
						case "가족호텔업":{
							for(int j=0;j<5;j++) {
								arealPath = "photo/Accommodation/"+acategory+"/"+fhotellList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
								list.get(i).setRealPath(arealPath);
							}
						}break;
						case "게스트하우스":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+guestList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "관광호텔업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+thotelList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "호스텔업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+hostelList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "휴양콘도미니엄업":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Accommodation/"+acategory+"/"+condoList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
					}
			}
			
		}
		
		totCnt = dao.findCountBydetailSearch(map);
		System.out.println(totCnt);
		totPage = (int) Math.ceil(totCnt/pageSize);
		int startPage = (pageNum-1)/pageGroup*pageGroup+1;
		int endPage = startPage+pageGroup-1;
		if(totPage < endPage) {
			endPage = totPage;
		}
		session.setAttribute("dscategory", category);
		session.setAttribute("minPrice", minPrice);
		session.setAttribute("maxPrice", maxPrice);
		mav.addObject("totPage", totPage);
		mav.addObject("startPage", startPage);
		mav.addObject("endPage", endPage);
		mav.addObject("list",list);
		return mav;
	}
	
	//검색어 초기화
	@GetMapping("/resetSearch")
	@ResponseBody
	public String resetSearch(HttpSession session) {
		session.removeAttribute("dscategory");
		session.removeAttribute("minPrice");
		session.removeAttribute("maxPrice");
		session.removeAttribute("keyword");
		session.removeAttribute("category");
		return "OK";
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
	
	// 숙소 상세페이지
	@GetMapping("/detail")
	public ModelAndView detail(int accommoNo, HttpSession session) {
		ModelAndView mav = new ModelAndView("Accommodation/Detail");
		
		// 로그인한 멤버
		MemberVO m = mdao.findByNo(7);
		session.setAttribute("loginM", m);
		
		AccommodationVO a = dao.findById(accommoNo);
		
		List<AccommodationVO> list = dao.findAllPhotoById(accommoNo);
		List<PhotoListVO> photoList = new ArrayList<>();
		
		String realPath = "";
		String category = a.getCategory();
		
		Random rand = new Random();
		String fhotellList[] = {"그림리조트", "꼬뜨도르가족호텔", "다인리조트", "베스트웨스턴 제주호텔", "올레리조트"};
		String guestList[] = {"민트게스트하우스", "섬게스트하우스", "슬로시티게스트하우스", "제주공항게스트하우스웨이브사운드", "토다게스트"};
		String thotelList[] = {"(주)호텔하니크라운", "제주썬호텔", "제주팔레스호텔", "글래드호텔앤리조트㈜ 메종글래드제주", "제주로얄호텔"};
		String hostelList[] = {"길리 리조트(구.협재 사계절 리조트)", "라이트프리(구. 에바다호스텔)", "아마스빌 리조트(구.아마스빌 호스텔)", "용두암캐빈", "해미안"};
		String condoList[] = {"메가리조트제주", "사조그랜드리조트", "이랜드파크 켄싱턴리조트 제주한림점", "일성제주콘도미니엄", "제주토비스콘도①"};
		
		if(list.size() > 4) {
			for(int i=0;i<list.size();i++) {
				PhotoListVO p = new PhotoListVO();
				a = list.get(i);
				p.setName(a.getName());
				p.setPath(a.getPath());
				p.setCategory(category);
				p.setRealPath("photo/Accommodation/"+p.getCategory()+"/"+p.getName()+"/"+p.getPath());
				
				p.setOrders(i);
				photoList.add(p);
			}
		}else {
			// 이미지 없을때 랜덤이미지
			System.out.println("동작");
				switch (category) {
					case "가족호텔업":{
						for(int i=0;i<5;i++) {
							PhotoListVO p = new PhotoListVO();
							try {
								if (list.get(i) != null && list.get(i).getOrders() == i+1) {
									
									p.setName(list.get(i).getName());
									p.setPath(list.get(i).getPath());
									p.setCategory(list.get(i).getCategory());
									p.setRealPath("photo/Accommodation/"+p.getCategory()+"/"+p.getName()+"/"+p.getPath());
									p.setOrders(list.get(i).getOrders());
									photoList.add(p);
								}else {
									realPath = "photo/Accommodation/"+category+"/"+fhotellList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
									p.setName(a.getName());
									p.setPath("acc"+(i+1)+".jpeg");
									p.setCategory(category);
									p.setRealPath(realPath);
									p.setOrders(i);
									photoList.add(p);
								}
							}catch(IndexOutOfBoundsException e) {
								realPath = "photo/Accommodation/"+category+"/"+fhotellList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
								p.setName(a.getName());
								p.setPath("acc"+(i+1)+".jpeg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
							
						}
					}break;
					case "게스트하우스":{
						for(int i=0;i<5;i++) {
							PhotoListVO p = new PhotoListVO();
							try {
								if (list.get(i) != null && list.get(i).getOrders() == i+1) {
									
									p.setName(list.get(i).getName());
									p.setPath(list.get(i).getPath());
									p.setCategory(list.get(i).getCategory());
									p.setRealPath("photo/Accommodation/"+p.getCategory()+"/"+p.getName()+"/"+p.getPath());
									p.setOrders(list.get(i).getOrders());
									photoList.add(p);
								}else {
									realPath = "photo/Accommodation/"+category+"/"+guestList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
									p.setName(a.getName());
									p.setPath("acc"+(i+1)+".jpeg");
									p.setCategory(category);
									p.setRealPath(realPath);
									p.setOrders(i);
									photoList.add(p);
								}
							}catch(IndexOutOfBoundsException e) {
								realPath = "photo/Accommodation/"+category+"/"+guestList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
								p.setName(a.getName());
								p.setPath("acc"+(i+1)+".jpeg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}
					}break;
					case "관광호텔업":{
						for(int i=0;i<5;i++) {
							PhotoListVO p = new PhotoListVO();
							try {
								if (list.get(i) != null && list.get(i).getOrders() == i+1) {
									
									p.setName(list.get(i).getName());
									p.setPath(list.get(i).getPath());
									p.setCategory(list.get(i).getCategory());
									p.setRealPath("photo/Accommodation/"+p.getCategory()+"/"+p.getName()+"/"+p.getPath());
									p.setOrders(list.get(i).getOrders());
									photoList.add(p);
								}else {
									realPath = "photo/Accommodation/"+category+"/"+thotelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
									p.setName(a.getName());
									p.setPath("acc"+(i+1)+".jpeg");
									p.setCategory(category);
									p.setRealPath(realPath);
									p.setOrders(i);
									photoList.add(p);
								}
							}catch(IndexOutOfBoundsException e) {
								realPath = "photo/Accommodation/"+category+"/"+thotelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
								p.setName(a.getName());
								p.setPath("acc"+(i+1)+".jpeg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}
					}break;
					case "호스텔업":{
						for(int i=0;i<5;i++) {
							PhotoListVO p = new PhotoListVO();
							try {
								if (list.get(i) != null && list.get(i).getOrders() == i+1) {
									
									p.setName(list.get(i).getName());
									p.setPath(list.get(i).getPath());
									p.setCategory(list.get(i).getCategory());
									p.setRealPath("photo/Accommodation/"+p.getCategory()+"/"+p.getName()+"/"+p.getPath());
									p.setOrders(list.get(i).getOrders());
									photoList.add(p);
								}else {
									realPath = "photo/Accommodation/"+category+"/"+hostelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
									p.setName(a.getName());
									p.setPath("acc"+(i+1)+".jpeg");
									p.setCategory(category);
									p.setRealPath(realPath);
									p.setOrders(i);
									photoList.add(p);
								}
							}catch(IndexOutOfBoundsException e) {
								realPath = "photo/Accommodation/"+category+"/"+hostelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
								p.setName(a.getName());
								p.setPath("acc"+(i+1)+".jpeg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}
					}break;
					case "휴양콘도미니엄업":{
						for(int i=0;i<5;i++) {
							PhotoListVO p = new PhotoListVO();
							try {
								if (list.get(i) != null && list.get(i).getOrders() == i+1) {
									
									p.setName(list.get(i).getName());
									p.setPath(list.get(i).getPath());
									p.setCategory(list.get(i).getCategory());
									p.setRealPath("photo/Accommodation/"+p.getCategory()+"/"+p.getName()+"/"+p.getPath());
									p.setOrders(list.get(i).getOrders());
									photoList.add(p);
								}else {
									realPath = "photo/Accommodation/"+category+"/"+condoList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
									p.setName(a.getName());
									p.setPath("acc"+(i+1)+".jpeg");
									p.setCategory(category);
									p.setRealPath(realPath);
									p.setOrders(i);
									photoList.add(p);
								}
							}catch(IndexOutOfBoundsException e) {
								realPath = "photo/Accommodation/"+category+"/"+condoList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
								p.setName(a.getName());
								p.setPath("acc"+(i+1)+".jpeg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}
					}break;
				}
		}
//		System.out.println(photoList);
//		mav.addObject("m", m);
		mav.addObject("a", a);
		mav.addObject("photoList", photoList);
		return mav;
	}
	
	// 결제 진행
	@PostMapping("/reservation")
	public ModelAndView payok(String imp_uid, String merchant_uid, 
			String paid_amount, String apply_num, ReservationVO r) {
		ModelAndView mav = new ModelAndView("redirect:/accommo/main");
		System.out.println("결제완료");
//		System.out.println("고유 ID: "+imp_uid);
//		System.out.println("상점거래 ID: "+merchant_uid);
//		System.out.println("결제금액: "+paid_amount);
//		System.out.println("카드 승인번호: "+apply_num);
//		System.out.println("date_s: "+r.getDate_s());
//		System.out.println("date_e: "+r.getDate_e());
		
		// ReservationVO(reserveNo=0, memberNo=0, accommoNo=0, totalPrice=0, 
		// date_s=2023-02-15, date_e=2023-02-23, headCount=3, imp_uid=imp_620242687294)
		r.setMemberNo(7);
		r.setTotalPrice(100);
		
//		System.out.println(r);
		
		int re = dao.makeReservation(r);
		if(re > 0) {
			// 결제 성공
		}else {
			// 결제 실패
		}
		return mav;
	}
	
	// 결제 위한 로그인 멤버 정보 불러오기
	@GetMapping("/getmember")
	@ResponseBody
	public String getMember() {
		MemberVO m = mdao.findByNo(7);
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(m);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
	
	// 찜 여부 결과
	@GetMapping("/findLike")
	@ResponseBody
	public int findLike(HttpServletRequest request, HttpSession session) {
		int re = 0;	//찜 x
		MemberVO m = (MemberVO) session.getAttribute("loginM");
		int accommoNo = Integer.parseInt(request.getParameter("accommoNo"));
		int memberNo = m.getMemberNo();
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("memberNo", memberNo);
		map.put("accommoNo", accommoNo);
		
		LikeVO l = null;
		l = dao.findLikeByM(map);
		if(l != null) {
			if (accommoNo == l.getRefNo()) {
				System.out.println("찜O");
				re = 1;
			}
		}else {
			System.out.println("찜X");
		}
		return re;
	}

	// 찜하기
	@GetMapping("/dolike")
	@ResponseBody
	public String dolike(HttpServletRequest request, HttpSession session) {
		int accommoNo = Integer.parseInt(request.getParameter("accommoNo"));
		System.out.println(accommoNo);
		LikeVO l = new LikeVO();
		l.setCategory("accommo");
		MemberVO m = (MemberVO) session.getAttribute("loginM");
		l.setMemberNo(m.getMemberNo());
		l.setRefNo(accommoNo);
		
		dao.doLike(l);
		return "찜완료";
	}
	
	// 찜해제
	@GetMapping("/unlike")
	@ResponseBody
	public String unlike(HttpServletRequest request, HttpSession session) {
		int accommoNo = Integer.parseInt(request.getParameter("accommoNo"));
		System.out.println(accommoNo);
		LikeVO l = new LikeVO();
		l.setCategory("accommo");
		MemberVO m = (MemberVO) session.getAttribute("loginM");
		l.setMemberNo(m.getMemberNo());
		l.setRefNo(accommoNo);
		
		dao.unLike(l);
		return "찜해제";
	}
}
