package com.uitgis.kras.service;

import java.util.List;

import com.uitgis.kras.model.User;

public interface UserService {
	
	User findByLoginId(String loginId);
	
	List<User> selectUserList();
	
	User selectUser(User user);
	int insertUser(User user);
	int updateUser(User user);
	int deleteUser(User user);
	
}
