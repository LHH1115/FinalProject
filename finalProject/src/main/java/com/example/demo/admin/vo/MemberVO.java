package com.example.demo.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {

	private int memberNo;
	private String name;
	private String jumin;
	private String id;
	private String pwd;
	private String addr;
	private String email;
	private String phone;
	private int point;
	private String role;
	private String mbti;
	private int rouletteCount;
	
	// 검색 필터
	private String type;
	private String keyword;
}
