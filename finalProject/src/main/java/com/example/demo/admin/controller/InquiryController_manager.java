package com.example.demo.admin.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.inquiry.dao.InquiryDAO;
import com.example.demo.inquiry.vo.InquiryVO;
import com.example.demo.reply.dao.ReplyDAO;

import lombok.Setter;

@Controller
@Setter
public class InquiryController_manager {
	
	
	@Autowired
	InquiryDAO inquiryDAO;
	
	@Autowired
	ReplyDAO replyDAO;
	
	int total_record=0;
	int page_size=10;
	int total_page=1;
	
	@GetMapping("/admin/inquiryManage")
	public Model selectAll(Model model,
			String search_col,
			String search_cat,
			String keyword,
			String reset,
			@RequestParam(value="pageNo", defaultValue = "1") int pageNo,
			HttpSession session
			) {
		if(reset!=null && reset.equals("all")) {
//			session.invalidate();
			session.removeAttribute("search_col");
			session.removeAttribute("search_cat");
			session.removeAttribute("keyword");
			

		}
		
		if(search_col==null || search_col.equals("")) {
			search_col=(String)session.getAttribute("search_col");
		}
		if(search_cat==null || search_cat.equals("")) {
			search_cat=(String)session.getAttribute("search_cat");
		}
		if(keyword==null || keyword.equals("")) {
			keyword=(String)session.getAttribute("keyword");
		}
		
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("search_cat", search_cat);
		map.put("search_col", search_col);
		map.put("keyword", keyword);		
		
		total_record = inquiryDAO.getTotalRecord(map);
		total_page = (int)Math.ceil((double)total_record/page_size);
		
		
		int start = page_size*(pageNo-1)+1;
		int end = start+page_size-1;
		if(end>total_record) {
			end=total_record;
		}
		map.put("start", start);
		map.put("end", end);
		
		List<InquiryVO> list=inquiryDAO.findAll(map);
		System.out.println(list.get(1).getReplyOk());
		
		session.setAttribute("search_col", search_col);
		session.setAttribute("search_cat", search_cat);
		session.setAttribute("keyword", keyword);
		
		
		model.addAttribute("list",list);
		model.addAttribute("total_page",total_page);
		model.addAttribute("this_page",pageNo);
		return model;
	}
	
	
	
	
}
