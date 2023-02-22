package com.example.demo.inquiry.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InquiryVO {

	private int inquiryNo;
	private int memberNo;
	private String title;
	private String content;
	private String category;
	private Date inqdate;
	
	private int replyOk;
	private String id;
	
}
