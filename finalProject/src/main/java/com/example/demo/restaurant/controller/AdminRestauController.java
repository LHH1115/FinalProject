package com.example.demo.restaurant.controller;

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

import com.example.demo.admin.dao.MemberDAO;
import com.example.demo.admin.vo.MemberVO;
import com.example.demo.restaurant.dao.RestaurantDAO;
import com.example.demo.restaurant.vo.PhotoListVO;
import com.example.demo.restaurant.vo.RestaurantPhotoVO;
import com.example.demo.restaurant.vo.RestaurantVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/restau")
@Setter
public class AdminRestauController {

	@Autowired
	private RestaurantDAO dao;
	
	@Autowired
	private MemberDAO mdao;
	
	@GetMapping("/update/{restauNo}")
	public ModelAndView updateForm(@PathVariable int restauNo, HttpSession session) {
		ModelAndView mav = new ModelAndView("Admin/Restaurant/Update");
		RestaurantVO r = dao.findById(restauNo);
		
		List<RestaurantVO> list = dao.findAllPhotoById(restauNo);
		List<PhotoListVO> photoList = new ArrayList<>();
		String realPath = "";
		String category = r.getCategory();
		
		Random rand = new Random();
		String koreanList[] = {"명가천지연무태장어", "제주광해애월점", "제주반딧불한담", "큰맘할매순대국", "푸른밤의해안속초식당"};
		String westernList[] = {"루마카", "반양", "카우보이스테이크하우스"};
		String japaneseList[] = {"스시앤", "아일랜드본섬", "해모둠", "해원앙", "혼참치"};
		
		if(list.size() > 4) {
			for(int i=0;i<list.size();i++) {
				PhotoListVO p = new PhotoListVO();
				r = list.get(i);
				p.setName(r.getName());
				p.setPath(r.getPath());
				p.setCategory(category);
				p.setRealPath("photo/Restaurant/"+p.getCategory()+"/"+p.getName()+"/"+p.getName()+"_"+(i+1)+".jpg");
				
				p.setOrders(i);
				photoList.add(p);
			}
		}else {
			// 이미지 없을때 랜덤이미지
			switch (category) {
				case "한식":{
					System.out.println("한식 case");
					String k = koreanList[rand.nextInt(5)];
					PhotoListVO p = new PhotoListVO();
					for(int i=0;i<5;i++) {
						try {
							if (list.get(i) != null && list.get(i).getOrders() == i+1) {
								p.setName(list.get(i).getName());
								p.setPath(list.get(i).getPath());
								p.setCategory(list.get(i).getCategory());
								p.setRealPath("photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg");
								p.setOrders(list.get(i).getOrders());
								photoList.add(p);
							}else {
								realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
								p.setName(r.getName());
								p.setPath(k+"_"+(i+1)+".jpg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}catch(IndexOutOfBoundsException e) {
							realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
							p.setName(r.getName());
							p.setPath(k+"_"+(i+1)+".jpg");
							p.setCategory(category);
							p.setRealPath(realPath);
							p.setOrders(i);
							photoList.add(p);
						}
					}
				}break;
				case "서양식":{
					String k = westernList[rand.nextInt(3)];
					PhotoListVO p = new PhotoListVO();
					for(int i=0;i<5;i++) {
						try {
							if (list.get(i) != null && list.get(i).getOrders() == i+1) {
								p.setName(list.get(i).getName());
								p.setPath(list.get(i).getPath());
								p.setCategory(list.get(i).getCategory());
								p.setRealPath("photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg");
								p.setOrders(list.get(i).getOrders());
								photoList.add(p);
							}else {
								realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
								p.setName(r.getName());
								p.setPath(k+"_"+(i+1)+".jpg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}catch(IndexOutOfBoundsException e) {
							realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
							p.setName(r.getName());
							p.setPath(k+"_"+(i+1)+".jpg");
							p.setCategory(category);
							p.setRealPath(realPath);
							p.setOrders(i);
							photoList.add(p);
						}
					}
				}break;
				case "일식":{
					String k = japaneseList[rand.nextInt(4)];
					PhotoListVO p = new PhotoListVO();
					for(int i=0;i<5;i++) {
						try {
							if (list.get(i) != null && list.get(i).getOrders() == i+1) {
								p.setName(list.get(i).getName());
								p.setPath(list.get(i).getPath());
								p.setCategory(list.get(i).getCategory());
								p.setRealPath("photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg");
								p.setOrders(list.get(i).getOrders());
								photoList.add(p);
							}else {
								realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
								p.setName(r.getName());
								p.setPath(k+"_"+(i+1)+".jpg");
								p.setCategory(category);
								p.setRealPath(realPath);
								p.setOrders(i);
								photoList.add(p);
							}
						}catch(IndexOutOfBoundsException e) {
							realPath = "photo/Restaurant/"+category+"/"+k+"/"+k+"_"+(i+1)+".jpg";
							p.setName(r.getName());
							p.setPath(k+"_"+(i+1)+".jpg");
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
		System.out.println(photoList);
		MemberVO m = mdao.findByNo(1);
		session.setAttribute("loginM", m);
		return mav;
	}
	
	@PostMapping("/updateSubmit")
	public ModelAndView updateSubmit(RestaurantVO r) {
		ModelAndView mav = new ModelAndView();
		int accommoNo = r.getRestauNo();
		mav.setViewName("redirect:/admin/accommo/update/"+accommoNo);
		System.out.println(r);
		// update 문
		int re = dao.updateById(r);
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
	public ModelAndView imgtest(MultipartHttpServletRequest mtpreq, HttpServletRequest request, String path, String newpath, int restauNo, String orders){
		ModelAndView mav = new ModelAndView();
		String pathArr[] = path.split(",");
		String newpathArr[] = newpath.split(",");
		String ordersArr[] = orders.split(",");
		List<MultipartFile> uploadFile = mtpreq.getFiles("uploadFile");
		
		RestaurantVO r = dao.findById(restauNo);
		
		String realpath = request.getServletContext().getRealPath("/photo");
		
		String savePath = (realpath+"\\Restaurant\\"+r.getCategory()+"\\"+r.getName());
		System.out.println(savePath);
		for(int i=0;i<pathArr.length;i++) {
			PhotoListVO p = new PhotoListVO();
			p.setCategory("");
			p.setName("");
			p.setOrders(Integer.parseInt(ordersArr[i]));
			if(!newpathArr[i].equals("unchanged")) {
				// 변경 O
				System.out.println("변경");
				
				RestaurantPhotoVO rp = new RestaurantPhotoVO();
				rp.setRestauNo(restauNo);
				rp.setPath("acc"+(i+1)+".jpeg");
				rp.setOrders((i+1));
				
				List<RestaurantVO> list = dao.findAllPhotoById(restauNo);
				if(list.size() > 4) {
					System.out.println("기존 사진 있음");
					// 기존 사진 있음
					// 이전 파일 삭제
					File file = new File(savePath + "/" + "acc"+(i+1)+".jpeg");
					file.delete();
				}else {
					System.out.println("기존 사진 없음");
					//dao.insertPhoto(rp);
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
		
		mav.setViewName("redirect:/admin/accommo/update/"+restauNo);
		return mav;
	}
}
