package com.cmsz.sldk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmsz.sldk.mapper.DkRecordMapper;
import com.cmsz.sldk.model.DkRecord;
import com.cmsz.sldk.service.DkRecordService;

@Service
@Transactional(readOnly = false)
public class DkRecordServiceImpl implements DkRecordService{
	
	@Autowired
	DkRecordMapper dkRecordMapper;

	@Override
	public boolean insert(DkRecord dkRecord) {
		return dkRecordMapper.insertSelective(dkRecord)==1?true:false;
	}
	
}
