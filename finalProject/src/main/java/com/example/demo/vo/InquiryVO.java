package com.example.demo.vo;



import lombok.Data;

@Data
public class InquiryVO {
	private int inquiryNo;
	private int memberNo;
	private String title;
	private String content;
	private String category;
	private String inqdate;
	
	private int replyOk;
	private String id;
	
}
