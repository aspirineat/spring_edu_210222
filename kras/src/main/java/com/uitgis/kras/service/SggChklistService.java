package com.uitgis.kras.service;

import com.uitgis.kras.model.SggChklist;

public interface SggChklistService {
	int insertSggChklist(SggChklist sggChklist);
	int deleteSggChklist(SggChklist sggChklist);
	int testTransaction();
}
