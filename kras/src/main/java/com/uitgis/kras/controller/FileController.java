package com.uitgis.kras.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.uitgis.kras.model.AttachFile;
import com.uitgis.kras.service.AttachFileService;
import com.uitgis.kras.util.KeyUtil;
import com.uitgis.kras.util.ValidUtil;

@RestController
@RequestMapping("api/files")
public class FileController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${attach.folderPath}")
	private String folderPath;

	@Autowired
	private AttachFileService attachFileService;
	
	
	@PostMapping(value="")
	public ResponseEntity<?> insertFile(MultipartHttpServletRequest multiFile) {
		
		List<MultipartFile> attachList = multiFile.getFiles("attach_file");
		List<AttachFile> attachfileList = new ArrayList<>();
		
		for (MultipartFile tempFile : attachList) {
			try {
				if (!tempFile.getOriginalFilename().isEmpty()) {
					String fileId = KeyUtil.getUUID();		
					
					String filePath = folderPath + File.separator + fileId;
					File file = new File(filePath);
					
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					
					tempFile.transferTo(file);			
					
					AttachFile fileVO = new AttachFile();
					fileVO.setFile_id(fileId);
					fileVO.setFile_name(tempFile.getOriginalFilename());
					fileVO.setFile_path(filePath);
					
					attachfileList.add(fileVO);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		int rs = 0;
		if (!attachfileList.isEmpty()) {
			rs = attachFileService.insertFileList(attachfileList);
		}
		
		return new ResponseEntity<>(rs > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{fileId}")
	public void downFile(@PathVariable String fileId, HttpServletResponse response) {
		AttachFile attachFile = attachFileService.selectFile(fileId);
		
		if (ValidUtil.empty(attachFile)) {
			new Exception();
		}
		
		String filePath = folderPath + File.separator + fileId;
		
		try {
			byte[] fileByte = FileUtils.readFileToByteArray(new File(filePath));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(attachFile.getFile_name(), "UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
