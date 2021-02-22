package com.uitgis.kras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uitgis.kras.service.HelloDbService;

@RestController
@RequestMapping("admin/hello")
public class AdminHelloApiController {

	@Autowired
	private HelloDbService helloDbService;
	
	@GetMapping("call")
	public String valueTest() {
		String rs = helloDbService.helloDb(); 
		return rs;
	}
}
