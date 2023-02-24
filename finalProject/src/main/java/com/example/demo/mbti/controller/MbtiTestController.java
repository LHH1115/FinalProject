package com.example.demo.mbti.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.mbti.vo.MbtiVO;

import lombok.Setter;

@Controller
@Setter
public class MbtiTestController {

	@GetMapping("/member/mbtiTest")
	public void mbtiTest() {
	}

	@PostMapping("/member/mbtiTest")
	public ModelAndView mbtiTest(MbtiVO vo, HttpSession session) {
		
		int first=vo.getNo1()+vo.getNo2()+vo.getNo3();
		int second=vo.getNo4()+vo.getNo5()+vo.getNo6();
		int third=vo.getNo7()+vo.getNo8()+vo.getNo9();
		int fourth=vo.getNo10()+vo.getNo11()+vo.getNo12();
		
		String A="E";
		if(first>=2) {
			A="I";
		}
		String B="N";
		if(second>=2) {
			B="S";
		}
		String C="F";
		if(third>=2) {
			C="T";
		}
		String D="P";
		if(fourth>=2) {
			D="J";
		}
		String MBTI = A+B+C+D;
		System.out.println(MBTI);
		
		session.setAttribute("MBTI", MBTI);
		
		ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}
	
}
