package com.example.demo.restaurant.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoListVO {

	private String realPath;
	private String category;
	private String name;
	private String path;
	private int orders;
}
