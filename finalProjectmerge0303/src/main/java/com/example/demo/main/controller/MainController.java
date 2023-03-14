package com.example.demo.main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.accommodation.dao.AccommoDAO;
import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.accommodation.vo.LikeVO;
import com.example.demo.attraction.dao.AttractionDAO;
import com.example.demo.attraction.vo.AttractionVO;
import com.example.demo.rentcar.dao.RentcarDAO;
import com.example.demo.rentcar.vo.RentcarVO;
import com.example.demo.restaurant.dao.RestaurantDAO;
import com.example.demo.restaurant.vo.RestaurantVO;

import lombok.Setter;

@Controller
@Setter
public class MainController {
	
	@Autowired
	private AccommoDAO accommodao;
	
	@Autowired
	private RestaurantDAO restaudao;
	
	@Autowired
	private AttractionDAO attractdao;

	@Autowired
	private RentcarDAO rentcardao;
	
	public RentcarVO setRealPath(int no) {
		RentcarVO vo=rentcardao.findByCarno(no);
		String category = vo.getCategory();
		String photopath=vo.getPhotoPath();
		vo.setRealPath("/photo/RentCar/"+category+"/"+photopath);
		// System.out.println(vo.getRealPath());
		return vo;
	}
	
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
	
	
	@GetMapping("/")
	public String main(Model model) {
		// 인기숙소
		List<LikeVO> accommoLikeList = accommodao.findMostLike(5);	// Top 5 나열
		List<AccommodationVO> accommoList = new ArrayList<>();
		AccommodationVO a = new AccommodationVO();
		for(int i=0;i<accommoLikeList.size();i++){
			int refNo = accommoLikeList.get(i).getRefNo();
			a = accommodao.findById(refNo);
			
			List<AccommodationVO> photo_list = accommodao.findAllPhotoById(refNo);
			String realPath = "";
			String category = a.getCategory();
			String name = "";
			String path = "";
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					AccommodationVO forPhoto = new AccommodationVO();
					forPhoto = photo_list.get(0);
					name = forPhoto.getName();
					path = forPhoto.getPath();
					realPath = "photo/Accommodation/"+category+"/"+name+"/"+path;
					a.setRealPath(realPath);
				}
			}else {
				Random rand = new Random();
				String fhotellList[] = {"그림리조트", "꼬뜨도르가족호텔", "다인리조트", "베스트웨스턴 제주호텔", "올레리조트"};
				String guestList[] = {"민트게스트하우스", "섬게스트하우스", "슬로시티게스트하우스", "제주공항게스트하우스웨이브사운드", "토다게스트"};
				String thotelList[] = {"(주)호텔하니크라운", "제주썬호텔", "제주팔레스호텔", "글래드호텔앤리조트㈜ 메종글래드제주", "제주로얄호텔"};
				String hostelList[] = {"길리 리조트(구.협재 사계절 리조트)", "라이트프리(구. 에바다호스텔)", "아마스빌 리조트(구.아마스빌 호스텔)", "용두암캐빈", "해미안"};
				String condoList[] = {"메가리조트제주", "사조그랜드리조트", "이랜드파크 켄싱턴리조트 제주한림점", "일성제주콘도미니엄", "제주토비스콘도①"};
					switch (category) {
						case "가족호텔업":{
							for(int j=0;j<5;j++) {
								realPath = "photo/Accommodation/"+category+"/"+fhotellList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
								a.setRealPath(realPath);
							}
						}break;
						case "게스트하우스":{
							for(int j=0;j<5;j++) {
								realPath = "photo/Accommodation/"+category+"/"+guestList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
								a.setRealPath(realPath);
							}
						}break;
						case "관광호텔업":{
							for(int j=0;j<5;j++) {
								realPath = "photo/Accommodation/"+category+"/"+thotelList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
								a.setRealPath(realPath);
							}
						}break;
						case "호스텔업":{
							for(int j=0;j<5;j++) {
								realPath = "photo/Accommodation/"+category+"/"+hostelList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
								a.setRealPath(realPath);
							}
						}break;
						case "휴양콘도미니엄업":{
							for(int j=0;j<5;j++) {
								realPath = "photo/Accommodation/"+category+"/"+condoList[rand.nextInt(5)]+"/acc"+(j+1)+".jpeg";
								a.setRealPath(realPath);
							}
						}break;
					}
			}
			accommoList.add(a);
		}
		model.addAttribute("accommoList", accommoList);
		
		// 인기식당
		List<com.example.demo.restaurant.vo.LikeVO> restauLikeList = restaudao.findMostLike(5);
		List<RestaurantVO> restauList = new ArrayList<>();
		RestaurantVO r = new RestaurantVO();
		for(int i=0;i<restauLikeList.size();i++){
			int refNo = restauLikeList.get(i).getRefNo();
			r = restaudao.findById(refNo);
			
			List<RestaurantVO> photo_list = restaudao.findAllPhotoById(refNo);
			String realPath = "";
			String category = r.getCategory();
			String name = "";
			String path = "";
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					RestaurantVO forPhoto = new RestaurantVO();
					forPhoto = photo_list.get(0);
					name = forPhoto.getName();
					path = forPhoto.getPath();
					realPath = "photo/Restaurant/"+category+"/"+name+"/"+path;
					r.setRealPath(realPath);
				}
			}else {
				Random rand = new Random();
				String koreanList[] = {"명가천지연무태장어", "제주광해애월점", "제주반딧불한담애월점", "큰맘할매순대국제주곽지점", "푸른밤의해안속초식당"};
				String westernList[] = {"루마카", "반양", "카우보이스테이크하우스"};
				String japaneseList[] = {"스시앤", "아일랜드본섬", "해모둠", "해원앙", "혼참치"};
				String chineseList[] = {"길림성", "대우반점", "만사성", "북경반점", "일빈관"};
				switch (category) {
					case "한식":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Restaurant/"+category+"/"+koreanList[rand.nextInt(5)]+"/rest"+(j+1)+".jpg";
							r.setRealPath(realPath);
						}
					}break;
					case "서양식":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Restaurant/"+category+"/"+westernList[rand.nextInt(3)]+"/rest"+(j+1)+".jpg";
							r.setRealPath(realPath);
						}
					}break;
					case "일식":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Restaurant/"+category+"/"+japaneseList[rand.nextInt(5)]+"/rest"+(j+1)+".jpg";
							r.setRealPath(realPath);
						}
					}break;
					case "중식":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Restaurant/"+category+"/"+chineseList[rand.nextInt(5)]+"/rest"+(j+1)+".jpg";
							r.setRealPath(realPath);
						}
					}break;
				}
			}
			restauList.add(r);
		}
		model.addAttribute("restauList", restauList);
		
		// 인기관광지
		List<com.example.demo.attraction.vo.LikeVO> list=attractdao.findMostLike(5);
		ArrayList<AttractionVO> attr_list = new ArrayList<>();
		for(com.example.demo.attraction.vo.LikeVO lvo : list) {
			int refNo=lvo.getRefNo();
			
			AttractionVO avo = attractdao.findById(refNo);
			
			List<AttractionVO> photo_list = attractdao.findAllPhotoById(refNo);
			String realPath = "";
			String category = "";
			String name = "";
			String path = "";
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					// 대표 이미지
					AttractionVO forPhoto = new AttractionVO();
					forPhoto = photo_list.get(0);
					category = avo.getCategory();
					category = render_category(category);
					name = forPhoto.getName();
					path = forPhoto.getPath();
					realPath = "photo/Attraction/"+category+"/"+name+"/"+path;
					// System.out.println("realphoto:"+realPath);
					avo.setRealPath(realPath);
					
				}
			}else {
				AttractionVO vo=attractdao.findById(refNo);
				category = vo.getCategory();
				category = render_category(category);
				Random r2 = new Random();
				int num = r2.nextInt(5)+1;
//				refno를 이용해서 tbl_attraction의 name, category, tbl_attractionphoto의 path를 가져와서
//				랜덤으로 랜더링하자.
				
				switch(category) {
				case "공원":
					String parkList[] = {"노리매","동백포레스트","휴애리 자연생활공원"};
					int n1 = r2.nextInt(parkList.length-1);
					name = parkList[n1];
					break;
				case "박물관":
					String museumList[] = {"양금석 가옥","의귀리 김만일묘역"};
					int n2 = r2.nextInt(museumList.length-1);
					name = museumList[n2];
					break;
				case "숲":
					String forestList[] = {"마흐니 숲길","큰엉해안경승지"};
					int n3 = r2.nextInt(forestList.length-1);
					name = forestList[n3];
					break;
				case "오름":
					String riseList[] = {"물영아리오름","사라오름"};
					int n4 = r2.nextInt(riseList.length-1);
					name = riseList[n4];
					break;
				case "테마파크":
					String themeParkList[] = {"코코몽 에코파크"};
					int n5 = r2.nextInt(themeParkList.length-1);
					name = themeParkList[n5];
				}
				
				realPath = "photo/Attraction/"+category+"/"+name+"/att"+num+".jpg";
				// System.out.println("fakephoto:"+realPath);
				avo.setRealPath(realPath);
			}
			attr_list.add(avo);
		}
		model.addAttribute("attract_list",attr_list);
		
//		인기 렌트카
		
		List<Integer> rent_list=rentcardao.findPopularCar();
		int no5=rent_list.get(4);
		rent_list.remove(4);
		List<Integer>rent_list2= rentcardao.findSameStarCar(no5);
		Random r3 = new Random();
		int re=r3.nextInt(rent_list2.size());
		rent_list.add(rent_list2.get(re));
		
		List<RentcarVO> top5InfoList = new ArrayList<>();
		for(int no:rent_list) {
			RentcarVO vo=setRealPath(no);
			top5InfoList.add(vo);
		}
		// System.out.println("ttt:"+top5InfoList);
		model.addAttribute("car_list",top5InfoList);
		
		return "Main/mainpage";
	}
}
