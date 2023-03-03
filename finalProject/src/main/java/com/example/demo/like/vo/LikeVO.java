package com.example.demo.like.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeVO {

	private int likeNo;
	private int memberNo;
	private String category;
	private int refNo;
	
}
