package com.example.demo.vo;

import lombok.Data;

@Data
public class ReviewVO {
	private int reviewNo;
	private int memberNo;
	private String title;
	private int accommoNo;
	private String content;
	private int stars;
	
}
