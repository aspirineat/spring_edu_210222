package com.uitgis.kras.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.uitgis.kras.model.User;

@Mapper
@Repository
public interface UserMapper {

	User selectUserByLoginId(String loginId);
	
	List<User> selectUserList();
	
	User selectUser(User user);
	int insertUser(User user);
	int updateUser(User user);
	int deleteUser(User user);
}
