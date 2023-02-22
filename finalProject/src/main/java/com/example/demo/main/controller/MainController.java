package com.example.demo.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Setter;

@Controller
@RequestMapping("/main")
@Setter
public class MainController {

	@GetMapping("")
	public String main() {
		return "Main/Header";
	}
}
