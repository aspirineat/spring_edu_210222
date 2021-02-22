package com.uitgis.kras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uitgis.kras.mapper.SggChklistMapper;
import com.uitgis.kras.model.SggChklist;
import com.uitgis.kras.service.SggChklistService;

@Service("sggChklistService")
public class SggChklistServiceImpl implements SggChklistService {

	@Autowired
	private SggChklistMapper sggChklistMapper;

	@Override
	public int insertSggChklist(SggChklist sggChklist) {
		return sggChklistMapper.insertSggChklist(sggChklist);
	}

	@Override
	public int deleteSggChklist(SggChklist sggChklist) {
		return sggChklistMapper.deleteSggChklist(sggChklist);
	}

	@Override
	public int testTransaction() {
		SggChklist insertOne = new SggChklist();
		insertOne.setSgg_code("11111");
		insertOne.setSgg_name("rollback 되서 안들어갈 데이터");
		
		SggChklist insertTwo = new SggChklist();
		insertTwo.setSgg_code("11110");
		insertTwo.setSgg_name("오류 발생!");
		
		sggChklistMapper.insertSggChklist(insertOne);
		sggChklistMapper.insertSggChklist(insertTwo);
		return 0;
	}

	
}
