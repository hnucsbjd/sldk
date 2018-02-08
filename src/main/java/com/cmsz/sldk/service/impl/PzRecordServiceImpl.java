package com.cmsz.sldk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmsz.sldk.mapper.PzRecordMapper;
import com.cmsz.sldk.model.PzRecord;
import com.cmsz.sldk.service.PzRecordService;

@Service
@Transactional(readOnly = false)
public class PzRecordServiceImpl implements PzRecordService{
	
	@Autowired
	PzRecordMapper pzMapper;

	@Override
	public boolean insert(PzRecord pzRecord) {
		return pzMapper.insertSelective(pzRecord)==1?true:false;
	}
	
}
