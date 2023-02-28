package com.example.demo.attraction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoListVO {

	private String introduction;
	private String closeddays;
	private String operatingtime;
	private String rateinformation;
	private String mainpurpose;
	private String parkingamount;
	private String facilities;
	private int orders;
}