package com.example.demo.vo.accommodation;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
	private MultipartFile uploadFile;
}
