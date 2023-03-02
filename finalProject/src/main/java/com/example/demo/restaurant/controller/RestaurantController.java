package com.example.demo.restaurant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
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

import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.admin.dao.MemberDAO;
import com.example.demo.member.vo.MemberVO;
import com.example.demo.restaurant.dao.RestaurantDAO;
import com.example.demo.restaurant.vo.LikeVO;
import com.example.demo.restaurant.vo.PhotoListVO;
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
					realPath = "photo/Restaurant/"+category+"/"+name+"/"+path;
					a.setRealPath(realPath);
				}
			}else {
				realPath = "photo/Restaurant/명가천지연무태장어/rest1.jpg";
				a.setRealPath(realPath);
			}
			restau_list.add(a);
		}
		// 관리자 여부 확인
		int role = 1;
		session.setAttribute("role", role);
		model.addAttribute("restau_list", restau_list);
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
		
		for(int i =0;i<list.size();i++) {
			int refNo = list.get(i).getRestauNo();
			List<RestaurantVO> photo_list = dao.findAllPhotoById(refNo);
			String rrealPath = "";
			String rcategory = list.get(i).getCategory();
			String rname = list.get(i).getName();
			String rpath = "";
			
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					RestaurantVO forPhoto = new RestaurantVO();
					forPhoto = photo_list.get(0);
					rpath = forPhoto.getPath();
					rrealPath = "photo/Restaurant/"+rcategory+"/"+rname+"/"+rpath;
					list.get(i).setRealPath(rrealPath);
				}
			}else {
				Random rand = new Random();
				String koreanList[] = {"명가천지연무태장어", "제주광해애월점", "제주반딧불한담애월점", "큰맘할매순대국제주곽지점", "푸른밤의해안속초식당"};
				String westernList[] = {"루마카", "반양", "카우보이스테이크하우스"};
				String japaneseList[] = {"스시앤", "아일랜드본섬", "해모둠", "해원앙", "혼참치"};
				String chineseList[] = {"길림성", "대우반점", "만사성", "북경반점", "일빈관"};
					switch (rcategory) {
						case "한식":{
							String name = koreanList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
								list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "서양식":{
							String name = westernList[rand.nextInt(3)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "일식":{
							String name = japaneseList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "중식":{
							String name = chineseList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
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
	public ModelAndView search(String keyword, int pageNum, HttpSession session) {
		ModelAndView mav = new ModelAndView("Restaurant/Search");
//			System.out.println("keyword:"+keyword);
//			System.out.println("category:"+category);
		
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
		
		for(int i =0;i<list.size();i++) {
			int refNo = list.get(i).getRestauNo();
			List<RestaurantVO> photo_list = dao.findAllPhotoById(refNo);
			String rrealPath = "";
			String rcategory = list.get(i).getCategory();
			String rname = list.get(i).getName();
			String rpath = "";
			
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					RestaurantVO forPhoto = new RestaurantVO();
					forPhoto = photo_list.get(0);
					rpath = forPhoto.getPath();
					rrealPath = "photo/Restaurant/"+rcategory+"/"+rname+"/"+rpath;
					list.get(i).setRealPath(rrealPath);
				}
			}else {
				Random rand = new Random();
				String koreanList[] = {"명가천지연무태장어", "제주광해애월점", "제주반딧불한담애월점", "큰맘할매순대국제주곽지점", "푸른밤의해안속초식당"};
				String westernList[] = {"루마카", "반양", "카우보이스테이크하우스"};
				String japaneseList[] = {"스시앤", "아일랜드본섬", "해모둠", "해원앙", "혼참치"};
				String chineseList[] = {"길림성", "대우반점", "만사성", "북경반점", "일빈관"};
					switch (rcategory) {
						case "한식":{
							String name = koreanList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
								list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "서양식":{
							String name = westernList[rand.nextInt(3)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "일식":{
							String name = japaneseList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "중식":{
							String name = chineseList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
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
	
	// 상세 검색
	@GetMapping("/detailSearch")
	public ModelAndView detailSearch(int pageNum, 
			String dscategory, String dskeyword, HttpSession session) {
		ModelAndView mav = new ModelAndView("Restaurant/Search");
		
		if(session.getAttribute("dscategory") != null) {
			dscategory = (String) session.getAttribute("dscategory");
		}
		if(session.getAttribute("dskeyword") != null) {
			dskeyword = (String) session.getAttribute("dskeyword");
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
		if(dscategory.equals("all")) {
			System.out.println("all");
			map.put("dscategory", "");
		}else {
			map.put("dscategory", dscategory);
		}
		if(dskeyword.equals("")) {
			map.put("dskeyword", "z");
		}else {
			map.put("dskeyword", dskeyword);
		}
		map.put("start", start);
		map.put("end", end);
		
		System.out.println(map);
		
		List<RestaurantVO> list = dao.detailSearch(map);
		
		for(int i =0;i<list.size();i++) {
			int refNo = list.get(i).getRestauNo();
			List<RestaurantVO> photo_list = dao.findAllPhotoById(refNo);
			String rrealPath = "";
			String rcategory = list.get(i).getCategory();
			String rname = list.get(i).getName();
			String rpath = "";
			
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					RestaurantVO forPhoto = new RestaurantVO();
					forPhoto = photo_list.get(0);
					rpath = forPhoto.getPath();
					rrealPath = "photo/Restaurant/"+rcategory+"/"+rname+"/"+rpath;
					list.get(i).setRealPath(rrealPath);
				}
			}else {
				Random rand = new Random();
				String koreanList[] = {"명가천지연무태장어", "제주광해애월점", "제주반딧불한담애월점", "큰맘할매순대국제주곽지점", "푸른밤의해안속초식당"};
				String westernList[] = {"루마카", "반양", "카우보이스테이크하우스"};
				String japaneseList[] = {"스시앤", "아일랜드본섬", "해모둠", "해원앙", "혼참치"};
				String chineseList[] = {"길림성", "대우반점", "만사성", "북경반점", "일빈관"};
					switch (rcategory) {
						case "한식":{
							String name = koreanList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
								list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "서양식":{
							String name = westernList[rand.nextInt(3)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "일식":{
							String name = japaneseList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
							}
						}break;
						case "중식":{
							String name = chineseList[rand.nextInt(5)];
							for(int j=0;j<5;j++) {
								rrealPath = "photo/Restaurant/"+rcategory+"/"+name+"/rest"+(j+1)+".jpg";
							list.get(i).setRealPath(rrealPath);
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
		session.setAttribute("dscategory", dscategory);
		session.setAttribute("dskeyword", dskeyword);
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
		session.removeAttribute("dskeyword");
		session.removeAttribute("keyword");
		return "OK";
	}
	
	// 사진 보유 여부 검색
	// 없으면 랜덤이미지 사용
	@GetMapping("/cnt")
	public String cntP() {
		RestaurantPhotoVO r = new RestaurantPhotoVO();
		r.setRestauNo(999);
		int re = dao.findPCnt(r);
		System.out.println(re);
		return "Restaurant/Main";
	}
	
	// 사진 리스트 검색
	@GetMapping("/findById")
	public String findById(Model model) {
		List<RestaurantVO> list = dao.findAllPhotoById(148);
		RestaurantVO r = new RestaurantVO();
		String realPath = "";
		String category = "";
		String name = "";
		String path = "";
		if(list.size() > 0) {
//			System.out.println(list);
			for(int i=0;i<list.size();i++) {
				//대표 이미지
				r = list.get(0);
				category = r.getCategory();
				name = r.getName();
				path = r.getPath();
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
		
		RestaurantVO r = dao.findById(restauNo);
		
		List<RestaurantVO> list = dao.findAllPhotoById(restauNo);
		List<PhotoListVO> photoList = new ArrayList<>();
		String realPath = "";
		String category = r.getCategory();
		
		Random rand = new Random();
		String koreanList[] = {"명가천지연무태장어", "제주광해애월점", "제주반딧불한담애월점", "큰맘할매순대국제주곽지점", "푸른밤의해안속초식당"};
		String westernList[] = {"루마카", "반양", "카우보이스테이크하우스"};
		String japaneseList[] = {"스시앤", "아일랜드본섬", "해모둠", "해원앙", "혼참치"};
		String chineseList[] = {"길림성", "대우반점", "만사성", "북경반점", "일빈관"};
		
		if(list.size() > 4) {
			for(int i=0;i<list.size();i++) {
				PhotoListVO p = new PhotoListVO();
				r = list.get(i);
				p.setName(r.getName());
				p.setPath(r.getPath());
				p.setCategory(category);
				p.setRealPath("photo/Restaurant/"+p.getCategory()+"/"+p.getName()+"/"+p.getPath());
				
				p.setOrders(i);
				photoList.add(p);
			}
		}else {
			// 이미지 없을때 랜덤이미지
			switch (category) {
				case "한식":{
					String name = koreanList[rand.nextInt(4)];
					for(int i=0;i<5;i++) {
						PhotoListVO p = new PhotoListVO();
						try {
							if (list.get(i) != null && list.get(i).getOrders() == i+1) {
								System.out.println(1);
								p.setName(list.get(i).getName());
								p.setPath(list.get(i).getPath());
								p.setCategory(list.get(i).getCategory());
								p.setRealPath("photo/Restaurant/"+category+"/"+p.getName()+"/"+p.getPath());
								p.setOrders(list.get(i).getOrders());
								photoList.add(p);
							}else {
								System.out.println(2);
								realPath = "photo/Restaurant/"+category+"/"+name+"/rest"+(i+1)+".jpg";
								p.setName(r.getName());
								p.setPath("rest"+(i+1)+".jpg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}catch(IndexOutOfBoundsException e) {
							System.out.println(i+":"+3);
							realPath = "photo/Restaurant/"+category+"/"+name+"/rest"+(i+1)+".jpg";
							p.setName(r.getName());
							p.setPath("rest"+(i+1)+".jpg");
							p.setCategory(category);
							p.setRealPath(realPath);
							p.setOrders(i);
							photoList.add(p);
						}
					}
				}break;
				case "서양식":{
					String name = westernList[rand.nextInt(4)];
					for(int i=0;i<5;i++) {
						PhotoListVO p = new PhotoListVO();
						try {
							if (list.get(i) != null && list.get(i).getOrders() == i+1) {
								p.setName(list.get(i).getName());
								p.setPath(list.get(i).getPath());
								p.setCategory(list.get(i).getCategory());
								p.setRealPath("photo/Restaurant/"+category+"/"+p.getName()+"/"+p.getPath());
								p.setOrders(list.get(i).getOrders());
								photoList.add(p);
							}else {
								realPath = "photo/Restaurant/"+category+"/"+name+"/rest"+(i+1)+".jpg";
								p.setName(r.getName());
								p.setPath("rest"+(i+1)+".jpg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}catch(IndexOutOfBoundsException e) {
							realPath = "photo/Restaurant/"+category+"/"+name+"/rest"+(i+1)+".jpg";
							p.setName(r.getName());
							p.setPath("rest"+(i+1)+".jpg");
							p.setCategory(category);
							p.setRealPath(realPath);
							p.setOrders(i);
							photoList.add(p);
						}
					}
				}break;
				case "일식":{
					String name = japaneseList[rand.nextInt(4)];
					for(int i=0;i<5;i++) {
						PhotoListVO p = new PhotoListVO();
						try {
							if (list.get(i) != null && list.get(i).getOrders() == i+1) {
								p.setName(list.get(i).getName());
								p.setPath(list.get(i).getPath());
								p.setCategory(list.get(i).getCategory());
								p.setRealPath("photo/Restaurant/"+category+"/"+p.getName()+"/"+p.getPath());
								p.setOrders(list.get(i).getOrders());
								photoList.add(p);
							}else {
								realPath = "photo/Restaurant/"+category+"/"+name+"/rest"+(i+1)+".jpg";
								p.setName(r.getName());
								p.setPath("rest"+(i+1)+".jpg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}catch(IndexOutOfBoundsException e) {
							realPath = "photo/Restaurant/"+category+"/"+name+"/rest"+(i+1)+".jpg";
							p.setName(r.getName());
							p.setPath("rest"+(i+1)+".jpg");
							p.setCategory(category);
							p.setRealPath(realPath);
							p.setOrders(i);
							photoList.add(p);
						}
					}
				}break;
				case "중식":{
					String name = chineseList[rand.nextInt(4)];
					for(int i=0;i<5;i++) {
						PhotoListVO p = new PhotoListVO();
						try {
							if (list.get(i) != null && list.get(i).getOrders() == i+1) {
								p.setName(list.get(i).getName());
								p.setPath(list.get(i).getPath());
								p.setCategory(list.get(i).getCategory());
								p.setRealPath("photo/Restaurant/"+category+"/"+p.getName()+"/"+p.getPath());
								p.setOrders(list.get(i).getOrders());
								photoList.add(p);
							}else {
								realPath = "photo/Restaurant/"+category+"/"+name+"/rest"+(i+1)+".jpg";
								p.setName(r.getName());
								p.setPath("rest"+(i+1)+".jpg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}catch(IndexOutOfBoundsException e) {
							realPath = "photo/Restaurant/"+category+"/"+name+"/rest"+(i+1)+".jpg";
							p.setName(r.getName());
							p.setPath("rest"+(i+1)+".jpg");
							p.setCategory(category);
							p.setRealPath(realPath);
							p.setOrders(i);
							photoList.add(p);
						}
					}
				}break;
			}
		}
		mav.addObject("r", r);
		mav.addObject("photoList", photoList);
		return mav;
	}
	
	// 찜 여부 결과
	@GetMapping("/findLike")
	@ResponseBody
	public int findLike(HttpServletRequest request, HttpSession session) {
		int re = 0;	//찜 x
		MemberVO m = (MemberVO) session.getAttribute("loginM");
		int restauNo = Integer.parseInt(request.getParameter("restauNo"));
		int memberNo = m.getMemberno();
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("memberNo", memberNo);
		map.put("restauNo", restauNo);
		
		LikeVO l = null;
		l = dao.findLikeByM(map);
		if(l != null) {
			if (restauNo == l.getRefNo()) {
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
		int restauNo = Integer.parseInt(request.getParameter("restauNo"));
		System.out.println(restauNo);
		LikeVO l = new LikeVO();
		l.setCategory("restau");
		MemberVO m = (MemberVO) session.getAttribute("loginM");
		l.setMemberNo(m.getMemberno());
		l.setRefNo(restauNo);
		
		dao.doLike(l);
		return "찜완료";
	}
	
	// 찜해제
	@GetMapping("/unlike")
	@ResponseBody
	public String unlike(HttpServletRequest request, HttpSession session) {
		int restauNo = Integer.parseInt(request.getParameter("restauNo"));
		System.out.println(restauNo);
		LikeVO l = new LikeVO();
		l.setCategory("restau");
		MemberVO m = (MemberVO) session.getAttribute("loginM");
		l.setMemberNo(m.getMemberno());
		l.setRefNo(restauNo);
		
		dao.unLike(l);
		return "찜해제";
	}

	
}
