package com.example.demo.restaurant.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantVO {

	private int attractNo;
	private String name;
	private String addr;
	private String phone;
	private String category;
	
	private int apNo;
	private String path;
	private int orders;
	
	private String realPath;
	private MultipartFile uploadFile;
}
