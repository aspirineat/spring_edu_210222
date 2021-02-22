package com.uitgis.kras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uitgis.kras.mapper.UserMapper;
import com.uitgis.kras.model.User;
import com.uitgis.kras.util.ValidUtil;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.selectUserByLoginId(username);
		
		if(ValidUtil.empty(user)) {
			new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		return user;
	}

}
