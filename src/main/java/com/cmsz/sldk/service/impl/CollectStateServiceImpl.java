package com.cmsz.sldk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmsz.sldk.mapper.CollectStateMapper;
import com.cmsz.sldk.model.CollectState;
import com.cmsz.sldk.service.CollectStateService;

@Service
@Transactional(readOnly = false)
public class CollectStateServiceImpl implements CollectStateService{
	
	@Autowired
	CollectStateMapper collectStateMapper;
	
	public boolean isExist(String openid) {
		boolean result = false;
		
		CollectState collectState = collectStateMapper.selectByOpenid(openid);
		if (null!=collectState)
			result = true;
		
		return result;
	}
	
	public CollectState selectByOpenid(String openid) {
		
		CollectState collectState = collectStateMapper.selectByOpenid(openid);
		
		return collectState;
	}

	@Override
	public boolean insert(CollectState dkState) {
		return collectStateMapper.insert(dkState)==1?true:false;
	}
	
	@Override
	public boolean updateCollectState(String openid, Integer currentNum) {
		return collectStateMapper.updateCollectState(openid, currentNum);
	}
	
}
