package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
		http.authorizeHttpRequests()
		.mvcMatchers("/","/member/**").permitAll()
		.anyRequest().authenticated();
		
		http.formLogin()
		.loginPage("/member/loginMember").permitAll()
		.defaultSuccessUrl("/myPage/loginok");
		
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.invalidateHttpSession(true).logoutSuccessUrl("/");
		
		http.httpBasic();
	}


}
