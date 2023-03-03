package com.example.demo.restaurant.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantPhotoVO {

	private int rpNo;
	private int restauNo;
	private String path;
	private int orders;
	
	private MultipartFile uploadFile;
}
