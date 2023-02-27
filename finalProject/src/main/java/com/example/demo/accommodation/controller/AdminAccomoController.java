package com.example.demo.accommodation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.accommodation.dao.AccommoDAO;
import com.example.demo.accommodation.vo.AccommoPhotoVO;
import com.example.demo.accommodation.vo.AccommodationVO;
import com.example.demo.accommodation.vo.PhotoListVO;
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
		List<PhotoListVO> photoList = new ArrayList<>();
		
		String realPath = "";
		String category = a.getCategory();
		if(list.size() > 0) {
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
			Random rand = new Random();
			String fhotellList[] = {"그림리조트", "꼬뜨도르가족호텔", "다인리조트", "베스트웨스턴 제주호텔", "올레리조트"};
			String guestList[] = {"민트게스트하우스", "섬게스트하우스", "슬로시티게스트하우스", "제주공항게스트하우스웨이브사운드", "토다게스트"};
			String thotelList[] = {"(주)호텔하니크라운", "제주썬호텔", "제주팔레스호텔", "글래드호텔앤리조트㈜ 메종글래드제주", "제주로얄호텔"};
			String hostelList[] = {"길리 리조트(구.협재 사계절 리조트)", "라이트프리(구. 에바다호스텔)", "아마스빌 리조트(구.아마스빌 호스텔)", "용두암캐빈", "해미안"};
			String condoList[] = {"메가리조트제주", "사조그랜드리조트", "이랜드파크 켄싱턴리조트 제주한림점", "일성제주콘도미니엄", "제주토비스콘도①"};
				switch (category) {
					case "가족호텔업":{
						for(int i=0;i<5;i++) {
							PhotoListVO p = new PhotoListVO();
							realPath = "photo/Accommodation/"+category+"/"+fhotellList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
							p.setName(a.getName());
							p.setPath("acc"+(i+1)+".jpeg");
							p.setCategory(category);
							p.setRealPath(realPath);
							p.setOrders(i);
							photoList.add(p);
						}
					}break;
					case "게스트하우스":{
						for(int i=0;i<5;i++) {
						PhotoListVO p = new PhotoListVO();
						realPath = "photo/Accommodation/"+category+"/"+guestList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
						p.setName(a.getName());
						p.setPath("acc"+(i+1)+".jpeg");
						p.setCategory(category);
						p.setRealPath(realPath);
						p.setOrders(i);
						photoList.add(p);
						}
					}break;
					case "관광호텔업":{
						for(int i=0;i<5;i++) {
						PhotoListVO p = new PhotoListVO();
						realPath = "photo/Accommodation/"+category+"/"+thotelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
						p.setName(a.getName());
						p.setPath("acc"+(i+1)+".jpeg");
						p.setCategory(category);
						p.setRealPath(realPath);
						p.setOrders(i);
						photoList.add(p);
						}
					}break;
					case "호스텔업":{
						for(int i=0;i<5;i++) {
						PhotoListVO p = new PhotoListVO();
						realPath = "photo/Accommodation/"+category+"/"+hostelList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
						p.setName(a.getName());
						p.setPath("acc"+(i+1)+".jpeg");
						p.setCategory(category);
						p.setRealPath(realPath);
						p.setOrders(i);
						photoList.add(p);
						}
					}break;
					case "휴양콘도미니엄업":{
						for(int i=0;i<5;i++) {
						PhotoListVO p = new PhotoListVO();
						realPath = "photo/Accommodation/"+category+"/"+condoList[rand.nextInt(5)]+"/acc"+(i+1)+".jpeg";
						p.setName(a.getName());
						p.setPath("acc"+(i+1)+".jpeg");
						p.setCategory(category);
						p.setRealPath(realPath);
						p.setOrders(i);
						photoList.add(p);
						}
					}break;
				}
		}
		mav.addObject("a", a);
		mav.addObject("photoList", photoList);
		// System.out.println(photoList);
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
	
	@PostMapping("/imgUpdate")
	public ModelAndView imgtest(MultipartHttpServletRequest mtpreq, HttpServletRequest request, String path, String newpath, int accommoNo, String orders){
		ModelAndView mav = new ModelAndView();
		String pathArr[] = path.split(",");
		String newpathArr[] = newpath.split(",");
		String ordersArr[] = orders.split(",");
		List<MultipartFile> uploadFile = mtpreq.getFiles("uploadFile");
		
		AccommodationVO a = dao.findById(accommoNo);
		
		String realpath = request.getServletContext().getRealPath("/photo");
		
		String savePath = (realpath+"\\Accommodation\\"+a.getCategory()+"\\"+a.getName());
		System.out.println(savePath);
		for(int i=0;i<pathArr.length;i++) {
			PhotoListVO p = new PhotoListVO();
			p.setCategory("");
			p.setName("");
			p.setOrders(Integer.parseInt(ordersArr[i]));
			if(!newpathArr[i].equals("unchanged")) {
				// 변경 O
				System.out.println("변경");
				
				AccommoPhotoVO ap = new AccommoPhotoVO();
				ap.setAccommoNo(accommoNo);
				ap.setPath("acc"+(i+1)+".jpeg");
				ap.setOrders((i+1));
				dao.insertPhoto(ap);
				
				List<AccommodationVO> list = dao.findAllPhotoById(accommoNo);
				if(list.size() > 4) {
					System.out.println("기존 사진 있음");
					// 기존 사진 있음
					// 이전 파일 삭제
					File file = new File(savePath + "/" + "acc"+(i+1)+".jpeg");
					file.delete();
				}else {
					System.out.println("기존 사진 없음");
					File folder = new File(savePath);
					if(!folder.exists()) {
						try {
							// 폴더 생성
							folder.mkdir();
						}catch(Exception e){
							// TODO: handle exception
							System.out.println("사진저장 예외: "+e.getMessage());
						}
					}
				}
				
				try {
					FileOutputStream fos = new FileOutputStream(savePath + "/" + "acc"+(i+1)+".jpeg");
					FileCopyUtils.copy(uploadFile.get(i).getBytes(),fos);
					fos.close();
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("사진저장 예외: "+e.getMessage());
				}
				p.setPath(newpathArr[i]);
				
			}else {
				// 변경 X
				System.out.println("변경 X");
				p.setPath(pathArr[i]);
			}
			
		}
		
		mav.setViewName("redirect:/admin/accommo/update/"+accommoNo);
		return mav;
	}
}
