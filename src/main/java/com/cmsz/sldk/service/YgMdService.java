package com.cmsz.sldk.service;

import com.cmsz.sldk.model.YgMd;

public interface YgMdService {
	
	boolean isExist(String openid);
	
	YgMd selectOne(YgMd ygmd);
}
