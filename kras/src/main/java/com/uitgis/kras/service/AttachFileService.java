package com.uitgis.kras.service;

import java.util.List;

import com.uitgis.kras.model.AttachFile;

public interface AttachFileService {
	
	AttachFile selectFile(String fileId);
	int insertFileList(List<AttachFile> attachFileList);
	
}
