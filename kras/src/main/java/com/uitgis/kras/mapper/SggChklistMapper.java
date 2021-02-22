package com.uitgis.kras.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.uitgis.kras.model.SggChklist;

@Mapper
@Repository
public interface SggChklistMapper {
	int insertSggChklist(SggChklist sggChklist);
	int deleteSggChklist(SggChklist sggChklist);
}
