package com.example.demo.vo.accommodation;

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
