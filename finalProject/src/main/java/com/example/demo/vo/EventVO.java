package com.example.demo.vo;

import java.util.Date;

import lombok.Data;

@Data
public class EventVO {
	private int eventno;
	private int memberno;
	private String eventdate;
	private String category;
	private int amount;
}
