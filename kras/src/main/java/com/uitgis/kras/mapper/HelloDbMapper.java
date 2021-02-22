package com.uitgis.kras.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HelloDbMapper {
	String helloDb();
}
