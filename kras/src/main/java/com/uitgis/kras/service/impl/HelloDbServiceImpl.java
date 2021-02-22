package com.uitgis.kras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uitgis.kras.mapper.HelloDbMapper;
import com.uitgis.kras.service.HelloDbService;

@Service("helloDbService")
public class HelloDbServiceImpl implements HelloDbService {

	@Autowired
	private HelloDbMapper helloDbMapper;

	@Override
	public String helloDb() {
		return helloDbMapper.helloDb();
	}
}
