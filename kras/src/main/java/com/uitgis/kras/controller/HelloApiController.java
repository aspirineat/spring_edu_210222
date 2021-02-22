package com.uitgis.kras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uitgis.kras.aop.LogExecutionTime;
import com.uitgis.kras.service.HelloDbService;
import com.uitgis.kras.service.SggChklistService;

@RestController
@RequestMapping("hello")
public class HelloApiController {

	@Autowired
	private HelloDbService helloDbService;
	
	@Autowired
	private SggChklistService sggChklistService;
	
	@LogExecutionTime
	@GetMapping("call")
	public String valueTest() {
		String rs = helloDbService.helloDb(); 
		return rs;
	}
	
	@PostMapping("authPost")
	public String testAuthPost() {
		String rs = helloDbService.helloDb(); 
		return rs;
	}
	
	@GetMapping("transaction") 
	public void testTransaction() {
		
		sggChklistService.testTransaction();
	}	
}
