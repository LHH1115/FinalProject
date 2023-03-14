package com.example.demo.attraction.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.admin.dao.MemberDAO;
import com.example.demo.admin.vo.MemberVO;
import com.example.demo.attraction.dao.AttractionDAO;
import com.example.demo.attraction.vo.AttractionInfoVO;
import com.example.demo.attraction.vo.AttractionPhotoVO;
import com.example.demo.attraction.vo.AttractionVO;
import com.example.demo.attraction.vo.InfoListVO;
import com.example.demo.attraction.vo.LikeVO;
import com.example.demo.like.dao.LikeDAO;

import lombok.Setter;

@Controller
@RequestMapping("/attract")
@Setter
public class AttractController {
	
	public int totPage = 1;		// 페이징 처리 위한 변수
	public int totCnt = 0;		// 페이징 처리 위한 변수
	public int pageSize = 6;	// 페이징 처리 위한 변수(검색 시 한페이지당 보여줄 결과 갯수)
	public int pageGroup = 10;	// 페이징 처리 위한 변수(페이징 번호 그룹 갯수)
	
	@Autowired
	private AttractionDAO dao;
	
	@Autowired
	private MemberDAO mdao;
	
	@Autowired
	private LikeDAO ldao;
	
	public String render_category(String acategory) {
		if(acategory.contains("공원")) {
			acategory="공원";
		}else if(acategory.contains("박물관")) {
			acategory="박물관";
		}else if(acategory.contains("숲")) {
			acategory="숲";
		}else if(acategory.contains("오름")) {
			acategory="오름";
		}else if(acategory.contains("테마파크")) {
			acategory="테마파크";
		}else {
			Random r = new Random();
			int re=r.nextInt(4);
			String[] c = {"공원","박물관","숲","오름","테마파크"};
			acategory = c[re];
		}
		return acategory;
	}
	
	
	@GetMapping("/main")
	public String attractMain(Model model, HttpSession session) {
		session.removeAttribute("keyword");		// 페이징 처리 세션 제거
		session.removeAttribute("category");	// 페이징 처리 세션 제거
		
		// 인기숙소
		List<LikeVO> like_list = dao.findMostLike(5);	// Top 5 나열
		LikeVO l = new LikeVO();
		List<AttractionVO> attract_list = new ArrayList<>();
		AttractionVO a = new AttractionVO();
		for(int i=0;i<like_list.size();i++){
			l = like_list.get(i);
			int refNo = l.getRefNo();
			a = dao.findById(refNo);
			
			List<AttractionVO> photo_list = dao.findAllPhotoById(refNo);
			String realPath = "";
			String category = "";
			String name = "";
			String path = "";
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					// 대표 이미지
					AttractionVO forPhoto = new AttractionVO();
					forPhoto = photo_list.get(0);
					category = a.getCategory();
					category = render_category(category);
					name = forPhoto.getName();
					path = forPhoto.getPath();
					realPath = "photo/Attraction/"+category+"/"+name+"/"+path;
					System.out.println("realphoto:"+realPath);
					a.setRealPath(realPath);
					
				}
			}else {
				AttractionVO vo=dao.findById(refNo);
				category = vo.getCategory();
				category = render_category(category);
				Random r = new Random();
				int num = r.nextInt(5)+1;
//				refno를 이용해서 tbl_attraction의 name, category, tbl_attractionphoto의 path를 가져와서
//				랜덤으로 랜더링하자.
				
				switch(category) {
				case "공원":
					String parkList[] = {"노리매","동백포레스트","휴애리 자연생활공원"};
					int n1 = r.nextInt(parkList.length-1);
					name = parkList[n1];
					break;
				case "박물관":
					String museumList[] = {"양금석 가옥","의귀리 김만일묘역"};
					int n2 = r.nextInt(museumList.length-1);
					name = museumList[n2];
					break;
				case "숲":
					String forestList[] = {"마흐니 숲길","큰엉해안경승지"};
					int n3 = r.nextInt(forestList.length-1);
					name = forestList[n3];
					break;
				case "오름":
					String riseList[] = {"물영아리오름","사라오름"};
					int n4 = r.nextInt(riseList.length-1);
					name = riseList[n4];
					break;
				case "테마파크":
					String themeParkList[] = {"코코몽 에코파크"};
					int n5 = r.nextInt(themeParkList.length-1);
					name = themeParkList[n5];
				}
				
//				realPath = "photo/Attraction/공원/노리매/att1.jpg";
				realPath = "photo/Attraction/"+category+"/"+name+"/att"+num+".jpg";
				System.out.println("fakephoto:"+realPath);
				a.setRealPath(realPath);
			}
			attract_list.add(a);
		}
		// 관리자 여부 확인
//		int role = 1;
//		session.setAttribute("role", role);
		model.addAttribute("attract_list", attract_list);
		return "Attraction/Main";
	}
	
	// 키워드 버튼 누를때
	@GetMapping("/searchC")
	public ModelAndView searchC(String keyword, int pageNum, HttpSession session) {
//		ModelAndView mav = new ModelAndView("Attraction/Search");
		ModelAndView mav = new ModelAndView("Attraction/Search");	// 사이드바 테스트 버전
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
		List<AttractionVO> list = dao.findByAny(map);
		
		for(int i =0;i<list.size();i++) {
			int refNo = list.get(i).getAttractNo();
			List<AttractionVO> photo_list = dao.findAllPhotoById(refNo);
			String arealPath = "";
			String acategory = list.get(i).getCategory();
			acategory=render_category(acategory);
			
			String aname = list.get(i).getName();
			String apath = "";
//			System.out.println(photo_list.size());
//			System.out.println("===================");
			
			if(photo_list.size() > 0) {
				System.out.println("true");
				for(int j=0;j<photo_list.size();j++) {
					AttractionVO forPhoto = new AttractionVO();
					forPhoto = photo_list.get(0);
					apath = forPhoto.getPath();
					arealPath = "photo/Attraction/"+acategory+"/"+aname+"/"+apath;
//					System.out.println("arealPath:"+arealPath);
					list.get(i).setRealPath(arealPath);
				}
			}else {
				Random rand = new Random();
				String parklList[] = {"노리매","동백포레스트","휴애리 자연생활공원"};
				String museumList[] = {"양금석 가옥","의귀리 김만일묘역"};
				String forestList[] = {"마흐니 숲길","큰엉해안경승지"};
				String riseList[] = {"물영아리오름","사라오름"};
				String themeParkList[] = {"코코몽 에코파크"};
				Random photo_random = new Random();
					switch (acategory) {
						case "공원":{
							for(int j=0;j<5;j++) {
								arealPath = "photo/Attraction/"+acategory+"/"+parklList[rand.nextInt(3)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
								list.get(i).setRealPath(arealPath);
							}
						}break;
						case "박물관":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Attraction/"+acategory+"/"+museumList[rand.nextInt(2)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "숲":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Attraction/"+acategory+"/"+forestList[rand.nextInt(2)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "오름":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Attraction/"+acategory+"/"+riseList[rand.nextInt(2)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
							list.get(i).setRealPath(arealPath);
							}
						}break;
						case "테마파크":{
							for(int j=0;j<5;j++) {
							arealPath = "photo/Attraction/"+acategory+"/"+themeParkList[0]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
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
		public ModelAndView search(String keyword, String category, int pageNum, String check, HttpSession session) {
			ModelAndView mav = new ModelAndView("Attraction/Search");
			if(check!=null && !check.equals("")) {
				session.removeAttribute("keyword");
				session.removeAttribute("category");
			}
			
			if(session.getAttribute("keyword") != null) {
				keyword = (String) session.getAttribute("keyword");
			}
			if(session.getAttribute("category") != null) {
				category = (String) session.getAttribute("category");
			}
			System.out.println("keyword:"+keyword);
			System.out.println("category:"+category);
			System.out.println("num:"+pageNum);
			
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
			map.put("category", category);
			List<AttractionVO> list = dao.findByAny(map);
			System.out.println("list:"+list);
			for(int i =0;i<list.size();i++) {
				int refNo = list.get(i).getAttractNo();
				List<AttractionVO> photo_list = dao.findAllPhotoById(refNo);
				String arealPath = "";
				String acategory = list.get(i).getCategory();
				acategory=render_category(acategory);
				
				String aname = list.get(i).getName();
				String apath = "";
				
				if(photo_list.size() > 0) {
//					System.out.println("true");
					for(int j=0;j<photo_list.size();j++) {
						AttractionVO forPhoto = new AttractionVO();
						forPhoto = photo_list.get(0);
						apath = forPhoto.getPath();
						arealPath = "photo/Attraction/"+acategory+"/"+aname+"/"+apath;
						list.get(i).setRealPath(arealPath);
					}
//					System.out.println("true:"+arealPath);
				}else {
					Random rand = new Random();
					String parklList[] = {"노리매","동백포레스트","휴애리 자연생활공원"};
					String museumList[] = {"양금석 가옥","의귀리 김만일묘역"};
					String forestList[] = {"마흐니 숲길","큰엉해안경승지"};
					String riseList[] = {"물영아리오름","사라오름"};
					String themeParkList[] = {"코코몽 에코파크"};
					Random photo_random = new Random();
						switch (acategory) {
							case "공원":{
								for(int j=0;j<5;j++) {
									arealPath = "photo/Attraction/"+acategory+"/"+parklList[rand.nextInt(3)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
									list.get(i).setRealPath(arealPath);
								}
							}break;
							case "박물관":{
								for(int j=0;j<5;j++) {
								arealPath = "photo/Attraction/"+acategory+"/"+museumList[rand.nextInt(2)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
								list.get(i).setRealPath(arealPath);
								}
							}break;
							case "숲":{
								for(int j=0;j<5;j++) {
								arealPath = "photo/Attraction/"+acategory+"/"+forestList[rand.nextInt(2)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
								list.get(i).setRealPath(arealPath);
								}
							}break;
							case "오름":{
								for(int j=0;j<5;j++) {
								arealPath = "photo/Attraction/"+acategory+"/"+riseList[rand.nextInt(2)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
								list.get(i).setRealPath(arealPath);
//								System.out.println(arealPath);
								}
							}break;
							case "테마파크":{
								for(int j=0;j<5;j++) {
								arealPath = "photo/Attraction/"+acategory+"/"+themeParkList[rand.nextInt(1)]+"/att"+(photo_random.nextInt(5)+1)+".jpg";
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
	
	// 사진 보유 여부 검색
	// 없으면 랜덤이미지 사용
	@GetMapping("/cnt")
	public String cntP() {
		AttractionPhotoVO a = new AttractionPhotoVO();
		a.setAttractNo(999);
		int re = dao.findPCnt(a);
		System.out.println(re);
		return "Attraction/Main";
	}
	
	// 사진 리스트 검색
	@GetMapping("/findById")
	public String findById(Model model) {
		List<AttractionVO> list = dao.findAllPhotoById(148);
		AttractionVO a = new AttractionVO();
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
				realPath = "photo/Attraction/"+category+"/"+name+"/"+path;
//				System.out.println(realPath);
//				System.out.println("next");
			}
		}else {
			System.out.println("이미지 없음");
		}
//		System.out.println(realPath);
		model.addAttribute("realPath",realPath);
		return "Attraction/Main";
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
		return "Attraction/Main";
	}
	
	// 관광지 상세페이지
	@GetMapping("/detail")
	public ModelAndView detail(int attractNo, HttpSession session) {
		ModelAndView mav = new ModelAndView("Attraction/Detail");
		
		AttractionVO a = dao.findById(attractNo);
		System.out.println("avo:"+a);
		List<AttractionInfoVO> atin = dao.findInfoById(attractNo);
//			atin.get(0).getOrders();
		
			InfoListVO invo = new InfoListVO();
			System.out.println("atin : "+atin);
			invo.setIntroduction(atin.get(0).getInfo().toString());
			invo.setCloseddays(atin.get(1).getInfo().toString());
			invo.setOperatingtime(atin.get(2).getInfo().toString());
			invo.setRateinformation(atin.get(3).getInfo().toString());
			invo.setMainpurpose(atin.get(4).getInfo().toString());
			invo.setParkingamount(atin.get(5).getInfo().toString());
			invo.setFacilities(atin.get(6).getInfo().toString());
			System.out.println(invo);

		List<AttractionVO> list = dao.findAllPhotoById(attractNo);
		List<String> photoList = new ArrayList<>();
		String realPath = "";
		String category = a.getCategory();
		category=render_category(category);
		
		String name = "";
		String path = "";
		
		if(list.size() > 0) {
			System.out.println("true");
			for(int i=0;i<list.size();i++) {
				a = list.get(i);
				name = a.getName();
				path = a.getPath();
				realPath = "photo/Attraction/"+category+"/"+name+"/"+path;
				photoList.add(realPath);
			}
		}else {
			System.out.println("nophoto");
			System.out.println(category);
			// 이미지 없을때 랜덤이미지
			Random rand = new Random();
			String parklList[] = {"노리매","동백포레스트","휴애리 자연생활공원"};
			String museumList[] = {"양금석 가옥","의귀리 김만일묘역"};
			String forestList[] = {"마흐니 숲길","큰엉해안경승지"};
			String riseList[] = {"물영아리오름","사라오름"};
			String themeParkList[] = {"코코몽 에코파크"};
			Random photo_random = new Random();
				switch (category) {
					case "숲":{						
						String k = forestList[rand.nextInt(2)];
						for(int i=0;i<5;i++) {
							realPath = "photo/Attraction/"+category+"/"+k+"/"+"att"+(photo_random.nextInt(5)+1)+".jpg";
							photoList.add(realPath);
						}
					}break;
					case "오름":{
						String k = riseList[rand.nextInt(2)];
						for(int i=0;i<5;i++) {
							realPath = "photo/Attraction/"+category+"/"+k+"/"+"att"+(photo_random.nextInt(5)+1)+".jpg";
						photoList.add(realPath);
						}
					}break;
					case "테마파크":{
						String k = themeParkList[rand.nextInt(1)];
						for(int i=0;i<5;i++) {
							realPath = "photo/Attraction/"+category+"/"+k+"/"+"att"+(photo_random.nextInt(5)+1)+".jpg";
						photoList.add(realPath);
						}
					}break;
					case "공원":{
						String k = parklList[rand.nextInt(3)];
						for(int i=0;i<5;i++) {
							realPath = "photo/Attraction/"+category+"/"+k+"/"+"att"+(photo_random.nextInt(5)+1)+".jpg";
						photoList.add(realPath);
						}
					}break;
					case "박물관":{
						String k = museumList[rand.nextInt(2)];
						for(int i=0;i<5;i++) {
							realPath = "photo/Attraction/"+category+"/"+k+"/"+"att"+(photo_random.nextInt(5)+1)+".jpg";
						photoList.add(realPath);
						}
					}break;
				}
			
//				System.out.println("대체 이미지: "+photoList);
		}
			System.out.println(photoList);
		mav.addObject("infoList", invo);
		mav.addObject("a", a);
		mav.addObject("photoList", photoList);
		return mav;
	}

	// 찜 여부 결과
	@GetMapping("/findLike")
	@ResponseBody
	public int findLike(HttpServletRequest request, HttpSession session) {
		int re = 0;	//찜 x
//		MemberVO m = (MemberVO) session.getAttribute("loginM");
		MemberVO m = mdao.findByNo(7);
		int attractNo = Integer.parseInt(request.getParameter("attractNo"));
		int memberNo = m.getMemberNo();
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("memberNo", memberNo);
		map.put("attractNo", attractNo);
		
		LikeVO l = null;
		l = dao.findLikeByM(map);
		if(l != null) {
			if (attractNo == l.getRefNo()) {
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
		int attractNo = Integer.parseInt(request.getParameter("attractNo"));
		System.out.println(attractNo);
		LikeVO l = new LikeVO();
		l.setCategory("attract");
//		MemberVO m = (MemberVO) session.getAttribute("loginM");
		MemberVO m = mdao.findByNo(7);
		l.setMemberNo(m.getMemberNo());
		l.setRefNo(attractNo);
		l.setLikeNo(ldao.getMaxNo());
		
		dao.doLike(l);
		return "찜완료";
	}
	
	// 찜해제
	@GetMapping("/unlike")
	@ResponseBody
	public String unlike(HttpServletRequest request, HttpSession session) {
		int attractNo = Integer.parseInt(request.getParameter("attractNo"));
		System.out.println(attractNo);
		LikeVO l = new LikeVO();
		l.setCategory("attract");
//		MemberVO m = (MemberVO) session.getAttribute("loginM");
		MemberVO m = mdao.findByNo(7);
		l.setMemberNo(m.getMemberNo());
		l.setRefNo(attractNo);
		
		dao.unLike(l);
		return "찜해제";
	}

	
}
