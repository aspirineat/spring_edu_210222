package com.uitgis.kras.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uitgis.kras.mapper.AttachFileMapper;
import com.uitgis.kras.model.AttachFile;
import com.uitgis.kras.service.AttachFileService;

@Service("attachFileService")
public class AttachFileServiceImpl implements AttachFileService {

	@Autowired
	private AttachFileMapper attachFileMapper;

	@Override
	public AttachFile selectFile(String fileId) {
		return attachFileMapper.selectFile(fileId);
	}

	@Override
	public int insertFileList(List<AttachFile> attachFileList) {
		return attachFileMapper.insertFileList(attachFileList);
	}

	
	
	
	
}
