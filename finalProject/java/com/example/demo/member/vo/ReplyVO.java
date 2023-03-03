package com.example.demo.member.vo;

import lombok.Data;

@Data
public class ReplyVO {
	private int replyno;
	private int inquiryno;
	private String content;
	private String repdate;
}
