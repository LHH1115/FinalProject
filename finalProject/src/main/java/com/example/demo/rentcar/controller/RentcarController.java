package com.example.demo.rentcar.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.like.dao.LikeDAO;
import com.example.demo.like.vo.LikeVO;
import com.example.demo.rentcar.dao.RentcarDAO;
import com.example.demo.rentcar.vo.RentcarVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Setter;

@Controller
@Setter
public class RentcarController {

	@Autowired
	RentcarDAO rentcarDAO;
	
	@Autowired
	LikeDAO likeDAO;
	
	public RentcarVO setRealPath(int no) {
		RentcarVO vo=rentcarDAO.findByCarno(no);
		String category = vo.getCategory();
		String photopath=vo.getPhotoPath();
		vo.setRealPath("/photo/RentCar/"+category+"/"+photopath);
		System.out.println(vo.getRealPath());
		return vo;
	}
	
	
	@GetMapping("/rentcar/main")
	public void mainPage(Model model) {
		List<Integer> list=rentcarDAO.findPopularCar();
		int no5=list.get(4);
		list.remove(4);
		List<Integer>list2= rentcarDAO.findSameStarCar(no5);
		Random r = new Random();
		int re=r.nextInt(list2.size());
		list.add(list2.get(re));
		
		List<RentcarVO> top5InfoList = new ArrayList<>();
		for(int no:list) {
			RentcarVO vo=setRealPath(no);
			top5InfoList.add(vo);
		}
		model.addAttribute("car_list",top5InfoList);
	}
	
	@GetMapping("/rentcar/Search")
	public void searchC(String category, 
			Model model,
			@RequestParam(value="pageNo", defaultValue="1") int pageNo,
			HttpSession session
			) {
		
		if(category==null||category.equals("")) {
			category=(String)session.getAttribute("s_category");
		}
		
		int total_record=0;
		int page_size=5;
		int total_page=1;
		HashMap<String, Object> map = new HashMap<>();
		total_record = rentcarDAO.countC(category);
		
		int start=(pageNo-1)*page_size+1;
		int end=start+page_size;
		if(end>total_record) {
			end=total_record;
		}
		model.addAttribute("category",category);
		
		map.put("start",start);
		map.put("end",end);
		map.put("category", category);
		
		List<RentcarVO> list = rentcarDAO.searchC(map);
		model.addAttribute("list",list);
		System.out.println(list);
		
		total_page=(int)Math.ceil((double)total_record/page_size);
		model.addAttribute("total_page",total_page);
		
		session.setAttribute("s_category",category);
		
	}
	
	
	@GetMapping("/rentcar/SearchCatAndName")
	public ModelAndView searchCatAndName(
			String category,
			String keyword,
			String flag,
			@RequestParam(value="pageNo", defaultValue="1") int pageNo,
			HttpSession session
			) {
		System.out.println("category : "+category);
		System.out.println("keyword : "+keyword);
		ModelAndView mav = new ModelAndView("/Rentcar/searchResult.html");
		
		
		if(flag==null || !flag.equals("session")) {
			if(keyword==null||keyword.equals("")) {
				keyword = (String)session.getAttribute("keyword");
			}
			if(category==null||category.equals("")) {
				category = (String)session.getAttribute("category");
			}
		}
		keyword=keyword.toLowerCase();
		
		int total_record=0;
		int page_size=5;
		int total_page=1;
		HashMap<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("category", category);
		total_record = rentcarDAO.countCatAndName(map);
		System.out.println("total_record:"+total_record);
		
		int start=(pageNo-1)*page_size+1;
		int end=start+page_size;
		if(end>total_record) {
			end=total_record;
		}
		mav.addObject("keyword",keyword);
		
		System.out.println("start:"+start);
		System.out.println("end:"+end);
		
		map.put("start",start);
		map.put("end",end);
		
		
		List<RentcarVO> list = rentcarDAO.searchCatAndName(map);
		mav.addObject("list", list);
		System.out.println(list);
		
		total_page=(int)Math.ceil((double)total_record/page_size);
		mav.addObject("total_page",total_page);
		
		session.setAttribute("keyword",keyword);
		session.setAttribute("category", category);
		
		return mav;
	}
	
	@GetMapping("/rentcar/Detail")
	public void detail(int carNo, Model model,HttpSession session) {
		RentcarVO vo = setRealPath(carNo);
		
//		List<String> list=rentcarDAO.findRentByCarno(carNo);
//		ArrayList<RentcarVO> list2 = new ArrayList<>();
//		for(String l:list) {
//			RentcarVO rvo=rentcarDAO.findRentInfoByStorename(l);
//			list2.add(rvo);
//		}
		List<RentcarVO> list2 = rentcarDAO.findStoreInfo(carNo);		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("category","RentCar");
		map.put("memberNo",1);
//		member에서 번호 받아와야한다.
		map.put("carNo",carNo);
		LikeVO lvo=likeDAO.findLikeByM(map);
		System.out.println("detail : "+vo);
		if(lvo !=null) {
			model.addAttribute("status","ok");
		}
		model.addAttribute("memberNo",1);
		model.addAttribute("detail",vo);
		model.addAttribute("rentstore",list2);
		session.setAttribute("detail",vo);
		session.setAttribute("rentstore", list2);
	}
	
	@GetMapping("/rentcar/storename")
	@ResponseBody
	public String getStore(int carNo) {
		List<String> list=rentcarDAO.findRentByCarno(carNo);
		ArrayList<RentcarVO> list2 = new ArrayList<>();
		for(String l:list) {
			RentcarVO vo=rentcarDAO.findRentInfoByStorename(l);
			list2.add(vo);
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonData = "";
		try {
			jsonData = mapper.writeValueAsString(list2);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonData;
		
	}
	
}
