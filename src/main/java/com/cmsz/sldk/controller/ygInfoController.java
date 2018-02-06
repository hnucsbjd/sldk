package com.cmsz.sldk.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmsz.sldk.service.YgInfoService;

@RestController
@RequestMapping("${adminPath}/yginfo")
public class ygInfoController {

	protected Logger    logger = LoggerFactory.getLogger( getClass() );
	
	@Autowired
	private YgInfoService ygInfoService;
	
	/**
	 * 员工是否已注册小程序
	 * @param openid	员工小程序唯一标示openid
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isexist/{openid}")
	public String isexist(@PathVariable("openid") String openid, HttpServletRequest request) {

		logger.info("openid: "+openid);
		boolean result = false;
		result = ygInfoService.isExist(openid);
		
		return String.valueOf(result);
	}
	
}
