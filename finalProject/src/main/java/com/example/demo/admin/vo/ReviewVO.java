package com.example.demo.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVO {

	private int reviewNo;
	private int memberNo;
	private String category;
	private int refNo;
	private String content;
	private int stars;
	
}
