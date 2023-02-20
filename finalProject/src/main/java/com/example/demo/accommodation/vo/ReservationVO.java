package com.example.demo.accommodation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationVO {

	private int reserveNo;
	private int memberNo;
	private int accommoNo;
	private int totalPrice;
	private String date_s;
	private String date_e;
	private int headCount;
	private String imp_uid;
}
