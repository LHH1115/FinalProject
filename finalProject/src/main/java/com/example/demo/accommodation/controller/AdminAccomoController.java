package com.example.demo.accommodation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.accommodation.dao.AccommoDAO;
import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.member.dao.MemberDAO;
import com.example.demo.member.vo.MemberVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/accommo")
@Setter
public class AdminAccomoController {

	@Autowired
	private AccommoDAO dao;
	
	@Autowired
	private MemberDAO mdao;
	
	@GetMapping("/update/{accommoNo}")
	public ModelAndView updateForm(@PathVariable int accommoNo, HttpSession session) {
		ModelAndView mav = new ModelAndView("Admin/Accommodation/Update");
		AccommodationVO a = dao.findById(accommoNo);
		
		List<AccommodationVO> list = dao.findAllPhotoById(accommoNo);
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
				realPath = "photo/Accommodation/"+category+"/"+name+"/"+path;
				photoList.add(realPath);
			}
		}else {
			// 이미지 없을때 랜덤이미지
			Random rand = new Random();
			String fhotellList[] = {"그림리조트", "꼬뜨도르가족호텔", "다인리조트", "베스트웨스턴 제주호텔", "올레리조트"};
			String guestList[] = {"민트게스트하우스", "섬게스트하우스", "슬로시티게스트하우스", "제주공항게스트하우스웨이브사운드", "토다게스트"};
			String thotelList[] = {"(주)호텔하니크라운", "제주썬호텔", "제주팔레스호텔", "글래드호텔앤리조트㈜ 메종글래드제주", "제주로얄호텔"};
			String hostelList[] = {"길리 리조트(구.협재 사계절 리조트)", "라이트프리(구. 에바다호스텔)", "아마스빌 리조트(구.아마스빌 호스텔)", "용두암캐빈", "해미안"};
			String condoList[] = {"메가리조트제주", "사조그랜드리조트", "이랜드파크 켄싱턴리조트 제주한림점", "일성제주콘도미니엄", "제주토비스콘도①"};
				switch (category) {
					case "가족호텔업":{
						for(int i=0;i<5;i++) {
							realPath = "photo/Accommodation/"+category+"/"+fhotellList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
							photoList.add(realPath);
						}
					}break;
					case "게스트하우스":{
						for(int i=0;i<5;i++) {
						realPath = "photo/Accommodation/"+category+"/"+guestList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
						photoList.add(realPath);
						}
					}break;
					case "관광호텔업":{
						for(int i=0;i<5;i++) {
						realPath = "photo/Accommodation/"+category+"/"+thotelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
						photoList.add(realPath);
						}
					}break;
					case "호스텔업":{
						for(int i=0;i<5;i++) {
						realPath = "photo/Accommodation/"+category+"/"+hostelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
						photoList.add(realPath);
						}
					}break;
					case "휴양콘도미니엄업":{
						for(int i=0;i<5;i++) {
						realPath = "photo/Accommodation/"+category+"/"+condoList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
						photoList.add(realPath);
						}
					}break;
				}
		}
		mav.addObject("a", a);
		mav.addObject("photoList", photoList);
		MemberVO m = mdao.findByNo(1);
		session.setAttribute("loginM", m);
		return mav;
	}
	
	@PostMapping("/updateSubmit")
	public ModelAndView updateSubmit(AccommodationVO a) {
		ModelAndView mav = new ModelAndView();
		int accommoNo = a.getAccommoNo();
		mav.setViewName("redirect:/admin/accommo/update/"+accommoNo);
		System.out.println(a);
		// update 문
		int re = dao.updateById(a);
		if(re > 0) {
			mav.addObject("update","yes");
		}else {
			mav.addObject("update","no");
		}
		return mav;
	}
	
	@GetMapping("/delete/{accommoNo}")
	public ModelAndView delete(@PathVariable int accommoNo) {
		ModelAndView mav = new ModelAndView("redirect:/admin/accommo/update/"+accommoNo);
		System.out.println(accommoNo);
//		int re = dao.deleteById(accommoNo);
		return mav;
	}
}
