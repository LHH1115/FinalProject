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
	private AttractionDAO attractionDAO;

	@GetMapping("/")
	public String main(Model model) {
		// 인기숙소
		List<LikeVO> accommo_like_list = accommodao.findMostLike(5);	// Top 5 나열
		LikeVO l = new LikeVO();
		List<AccommodationVO> accommo_list = new ArrayList<>();
		AccommodationVO a = new AccommodationVO();
		for(int i=0;i<accommo_like_list.size();i++){
			l = accommo_like_list.get(i);
			int refNo = l.getRefNo();
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
			accommo_list.add(a);
		}
		model.addAttribute("accommo_list", accommo_list);
		
		// 인기식당
		List<com.example.demo.restaurant.vo.LikeVO> restaulike_list = restaudao.findMostLike(5);
		com.example.demo.restaurant.vo.LikeVO lr = new com.example.demo.restaurant.vo.LikeVO();
		List<RestaurantVO> restau_list = new ArrayList<>();
		RestaurantVO r = new RestaurantVO();
		for(int i=0;i<restaulike_list.size();i++){
			lr = restaulike_list.get(i);
			int refNo = lr.getRefNo();
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
			restau_list.add(r);
		}
		model.addAttribute("restau_list", restau_list);
		
		// 인기관광지
		List<com.example.demo.attraction.vo.LikeVO> attractlike_list = attractionDAO.findMostLike(5);
		com.example.demo.attraction.vo.LikeVO lrr = new com.example.demo.attraction.vo.LikeVO();
		List<AttractionVO> attract_list = new ArrayList<>();
		AttractionVO aa = new AttractionVO();
		for(int i=0;i<attractlike_list.size();i++){
			lrr = attractlike_list.get(i);
			int refNo = lrr.getRefNo();
			aa = attractionDAO.findById(refNo);
			
			List<AttractionVO> photo_list = attractionDAO.findAllPhotoById(refNo);
			String realPath = "";
			String category = aa.getCategory();
			String name = "";
			String path = "";
			if(photo_list.size() > 0) {
				for(int j=0;j<photo_list.size();j++) {
					AttractionVO forPhoto = new AttractionVO();
					forPhoto = photo_list.get(0);
					name = forPhoto.getName();
					path = forPhoto.getPath();
					realPath = "photo/Attraction/"+category+"/"+name+"/"+path;
					aa.setRealPath(realPath);
				}
			}else {
				Random rand = new Random();
				String parklList[] = {"노리매","동백포레스트","휴애리 자연생활공원"};
				String museumList[] = {"양금석 가옥","의귀리 김만일묘역"};
				String forestList[] = {"마흐니 숲길","큰엉해안경승지"};
				String riseList[] = {"물영아리오름","사라오름"};
				String themeParkList[] = {"코코몽 에코파크"};
				switch (category) {
					case "공원":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Attraction/"+category+"/"+parklList[rand.nextInt(3)]+"/att"+(j+1)+".jpg";
							aa.setRealPath(realPath);
						}
					}break;
					case "박물관":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Attraction/"+category+"/"+museumList[rand.nextInt(2)]+"/att"+(j+1)+".jpg";
							aa.setRealPath(realPath);
						}
					}break;
					case "숲":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Attraction/"+category+"/"+forestList[rand.nextInt(2)]+"/att"+(j+1)+".jpg";
							aa.setRealPath(realPath);
						}
					}break;
					case "오름":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Attraction/"+category+"/"+riseList[rand.nextInt(2)]+"/att"+(j+1)+".jpg";
							aa.setRealPath(realPath);
						}
					}break;
					case "테마파크":{
						for(int j=0;j<5;j++) {
							realPath = "photo/Attraction/"+category+"/"+themeParkList[rand.nextInt(1)]+"/att"+(j+1)+".jpg";
							aa.setRealPath(realPath);
						}
					}break;
				}
			}
			attract_list.add(aa);
		}
		model.addAttribute("attract_list", attract_list);
		
		return "Main/mainpage";
	}
}
