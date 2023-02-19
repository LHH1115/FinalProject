package com.example.demo.accommodation.vo;

import java.util.Date;

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
	private Date date_s;
	private Date date_e;
	private int headCount;
}
