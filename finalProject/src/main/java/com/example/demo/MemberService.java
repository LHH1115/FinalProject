package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.member.dao.UserMemberDAO;
import com.example.demo.member.vo.MemberVO;

import lombok.Setter;


@Service
@Setter
public class MemberService implements UserDetailsService{

	@Autowired
	private UserMemberDAO dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		MemberVO m = dao.findById(username);
		if(m == null) {
			System.out.println("error");
			throw new UsernameNotFoundException(username);
		}
		
		UserDetails user = User.builder()
							.username(username)
							.password(m.getPwd())
							.roles(m.getRole()).build();
		System.out.println(user);
		return user;
	}

	
}
