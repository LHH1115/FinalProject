package com.example.demo.attraction.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttractionInfoVO {

	private int atiNo;
	private int attractNo;
	private String info;
	private int orders;
	
	private MultipartFile uploadFile;
}
