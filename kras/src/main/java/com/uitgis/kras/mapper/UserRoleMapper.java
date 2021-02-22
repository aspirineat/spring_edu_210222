package com.uitgis.kras.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.uitgis.kras.model.UserRole;

@Mapper
@Repository
public interface UserRoleMapper {

	int insertRoleList(List<UserRole> roleList);
}
