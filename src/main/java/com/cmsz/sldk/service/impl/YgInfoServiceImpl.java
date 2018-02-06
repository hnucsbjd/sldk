package com.cmsz.sldk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmsz.sldk.mapper.YgInfoMapper;
import com.cmsz.sldk.model.YgInfo;
import com.cmsz.sldk.service.YgInfoService;

@Service
@Transactional(readOnly = false)
public class YgInfoServiceImpl implements YgInfoService{
	
	@Autowired
	YgInfoMapper ygInfoMapper;
	
	public boolean isExist(String openid) {
		boolean result = false;
		
		YgInfo yginfo = ygInfoMapper.selectByOpenid(openid);
		if (null!=yginfo)
			result = true;
		
		return result;
	}
	
	public YgInfo selectByOpenid(String openid) {
		
		YgInfo yginfo = ygInfoMapper.selectByOpenid(openid);
		
		return yginfo;
	}
	
	public List<String> selectAllOpenid() {
		return ygInfoMapper.selectAllOpenid();
	}

	@Override
	public boolean insert(YgInfo ygInfo) {
		return ygInfoMapper.insert(ygInfo)==1?true:false;
	}
	
}
