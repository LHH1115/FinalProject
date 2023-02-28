package com.example.demo.rentcar.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentcarVO {
	private int rentNo;
	private String name;
	private String addr;
	private String phone;
	private int stars;
	
	private int carNo;
	private String modelName;
	private String photoPath;
	private String category;
	private int price;
	
	private String realPath;
	
}
