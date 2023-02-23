package com.example.demo.attraction.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttractionPhotoVO {

	private int apNo;
	private int attractNo;
	private String path;
	private int orders;
	
	private MultipartFile uploadFile;
}
