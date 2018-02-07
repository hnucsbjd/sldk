package com.cmsz.sldk.service;

import com.cmsz.sldk.model.DkState;

public interface DkStateService {
	boolean isExist(String openid);
	
	public DkState selectByOpenid(String openid);
	
	public boolean insert(DkState dkState);
	
	public boolean updateDkState(String openid, Integer currentNum);
}
