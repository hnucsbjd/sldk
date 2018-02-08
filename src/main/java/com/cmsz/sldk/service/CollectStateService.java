package com.cmsz.sldk.service;

import com.cmsz.sldk.model.CollectState;

public interface CollectStateService {
	boolean isExist(String openid);
	
	public CollectState selectByOpenid(String openid);
	
	public boolean insert(CollectState collectState);
	
	public boolean updateCollectState(String openid, Integer currentNum);
}
