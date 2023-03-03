package com.example.demo.member.vo;

import lombok.Data;

@Data
public class AccommodationVO {
	private int accommoNo;
	private String name;
	private String addr;
	private String phone;
	private int price;
	private String category;
	
	private int apNo;
	private String path;
	private int orders;
	
	private String realPath;
}
