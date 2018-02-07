package com.cmsz.sldk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmsz.sldk.mapper.DkStateMapper;
import com.cmsz.sldk.model.DkState;
import com.cmsz.sldk.service.DkStateService;

@Service
@Transactional(readOnly = false)
public class DkStateServiceImpl implements DkStateService{
	
	@Autowired
	DkStateMapper dkStateMapper;
	
	public boolean isExist(String openid) {
		boolean result = false;
		
		DkState dkState = dkStateMapper.selectByOpenid(openid);
		if (null!=dkState)
			result = true;
		
		return result;
	}
	
	public DkState selectByOpenid(String openid) {
		
		DkState dkState = dkStateMapper.selectByOpenid(openid);
		
		return dkState;
	}

	@Override
	public boolean insert(DkState dkState) {
		return dkStateMapper.insert(dkState)==1?true:false;
	}
	
	@Override
	public boolean updateDkState(String openid, Integer currentNum) {
		return dkStateMapper.updateDkState(openid, currentNum);
	}
	
}
