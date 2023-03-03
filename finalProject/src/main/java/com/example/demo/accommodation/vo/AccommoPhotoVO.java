package com.example.demo.accommodation.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommoPhotoVO {

	private int apNo;
	private int accommoNo;
	private String path;
	private int orders;
	
	private MultipartFile uploadFile;
}
