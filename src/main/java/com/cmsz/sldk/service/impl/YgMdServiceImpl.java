package com.cmsz.sldk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmsz.sldk.mapper.YgMdMapper;
import com.cmsz.sldk.model.YgMd;
import com.cmsz.sldk.service.YgMdService;

@Service
@Transactional(readOnly = false)
public class YgMdServiceImpl implements YgMdService{
	
	@Autowired
	YgMdMapper ygMdMapper;
	
	public boolean isExist(String openid) {
		boolean result = false;
		YgMd ygMd = ygMdMapper.selectByOpenid(openid);
		if (null!=ygMd)
			result = true;
		
		return result;
	}

	@Override
	public YgMd selectOne(YgMd ygmd) {
		// TODO 
		return ygMdMapper.selectOne(ygmd);
	}
}
