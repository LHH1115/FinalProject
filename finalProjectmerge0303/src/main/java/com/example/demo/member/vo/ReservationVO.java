package com.example.demo.member.vo;

import lombok.Data;

@Data
public class ReservationVO {
	private int memberNo;
	private int accommoNo;
	private int totalPrice;
	private String date_s;
	private String date_e;
	private int headCount;
	private String imp_uid;
	
	private String name;
}
