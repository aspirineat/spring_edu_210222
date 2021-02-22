package com.uitgis.kras.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uitgis.kras.mapper.UserMapper;
import com.uitgis.kras.mapper.UserRoleMapper;
import com.uitgis.kras.model.User;
import com.uitgis.kras.model.UserRole;
import com.uitgis.kras.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public User findByLoginId(String loginId) {
		return userMapper.selectUserByLoginId(loginId);
	}

	@Override
	public List<User> selectUserList() {
		return userMapper.selectUserList();
	}

	@Override
	public User selectUser(User user) {
		return userMapper.selectUser(user);
	}
	
	@Override
	public int insertUser(User user) {
		int rsUser = userMapper.insertUser(user);
		
		int rsRole = 0;
		if (user.getRole_type() != null && !user.getRole_type().isEmpty()) {
			String userId = user.getUser_id();
			
			List<UserRole> roleList = user.getRole_type()
					.stream()
					.map(role -> {
						UserRole tempRole = new UserRole();
						tempRole.setUser_id(userId);
						tempRole.setRole_type(role);
						return tempRole;
					}).collect(Collectors.toList());
			
			rsRole = userRoleMapper.insertRoleList(roleList);
		}
		
		return rsUser < 0 || rsRole < 0 ? -1 : 1;
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateUser(user);
	}

	@Override
	public int deleteUser(User user) {
		return userMapper.deleteUser(user);
	}

}
