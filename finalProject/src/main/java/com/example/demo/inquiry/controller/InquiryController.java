package com.example.demo.inquiry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InquiryController {

	@GetMapping("/main/inquiry")
	public String inquiryForm() {
		return "main/inquiry";
	}
}
