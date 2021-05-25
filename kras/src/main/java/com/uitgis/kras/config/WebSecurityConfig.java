package com.uitgis.kras.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.uitgis.kras.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver=new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}
	
	@Override
	public void configure(WebSecurity http) throws Exception {
		http.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
			.httpBasic().disable() // http 기본인증 disable
			.csrf().disable() // csrf 보안 토큰 disable
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
			.and()
			.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/**").hasRole("PUT")
			.antMatchers(HttpMethod.DELETE, "/api/**").hasRole("DELETE")
			.antMatchers("/api/**").hasRole("API")
			.anyRequest().permitAll();
			
	    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	// test case
	
	// * 로그인 ---------------------------------------------------------------------
	// 1. admin/admin 로그인
	// url > http://localhost:8080/kras/jwt/login
	// Header 
	//  - Accept : application/json
	//  - Content-Type : application/json;charset=UTF-8
	// Body
	/*
	 {
		"login_id": "admin",
	    "passwd": "admin"
	 }
	*/
	// 2. user/user 로그인
	
	// * admin url 호출-------------------------------------------------------------
	// url > http://localhost:8080/kras/admin/hello/call
	// Header
	//  - Accept : application/json
	//  - Content-Type : application/json;charset=UTF-8
	//  - X-AUTH-TOKEN : 로그인 token
	// admin 계정 / user 계정 각각 호출
	
	// * PUT url 호출---------------------------------------------------------------
	// url > http://localhost:8080/kras/api/users/test
	// Header
	//  - Accept : application/json
	//  - Content-Type : application/json;charset=UTF-8
	//  - X-AUTH-TOKEN : 로그인 token
	// Body
	/*
	 {
	    "passwd": "test2"
	 }
	*/
	// user 계정 / test 계정 각각 호출
	
	
}
