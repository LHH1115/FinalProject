package com.example.demo.reply.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyVO {
	private int replyNo;
	private int inquiryNo;
	private String content;
	private Date regdate;
	
}
