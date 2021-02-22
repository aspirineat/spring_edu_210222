package com.uitgis.kras.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.uitgis.kras.model.AttachFile;

@Mapper
@Repository
public interface AttachFileMapper {
	
	AttachFile selectFile(String fileId);
	int insertFileList(List<AttachFile> attachFileList);
	
}
