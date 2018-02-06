package com.cmsz.sldk.service;

import java.util.List;

import com.cmsz.sldk.model.YgInfo;

public interface YgInfoService {
	boolean isExist(String openid);
	
	/**
	 * 通过openid获取员工信息
	 * @param openid
	 * @return	YgInfo
	 */
	public YgInfo selectByOpenid(String openid);
	
	public List<String> selectAllOpenid();
	
	boolean insert(YgInfo ygInfo);
}
