package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDAO;
import com.example.demo.vo.MemberVO;

import lombok.Setter;


@Service
@Setter
public class MemberService implements UserDetailsService{

	@Autowired
	private MemberDAO dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("사용자 로그인 처리");
		System.out.println("username:"+username);
		MemberVO m = dao.findById(username);
		if(m == null) {
			throw new UsernameNotFoundException(username);
		}
		
		UserDetails user = User.builder()
							.username(username)
							.password(m.getPwd())
							.roles(m.getRole()).build();
		return user;
	}

	
}
