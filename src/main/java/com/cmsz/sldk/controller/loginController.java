package com.cmsz.sldk.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmsz.sldk.model.YgInfo;
import com.cmsz.sldk.model.YgMd;
import com.cmsz.sldk.service.YgInfoService;
import com.cmsz.sldk.service.YgMdService;
import com.xiaoleilu.hutool.lang.Base64;

@RestController
@RequestMapping("${adminPath}/login")
public class loginController {

	protected Logger    logger = LoggerFactory.getLogger( getClass() );
	
	@Autowired
	private YgInfoService ygInfoService;
	@Autowired
	private YgMdService ygMdService;
	
	/**
	 * 注册
	 * @param infos		数据组织方式：name|gh|phonenumberDh|phonenumber|openid
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/register/{infos}")
	public String register(@PathVariable("infos") String infos, HttpServletRequest request) throws UnsupportedEncodingException {
		
		boolean result = false;
		logger.info("register infos: "+infos);
		//java base64解密
		String decodeInfos = Base64.decodeStr(infos);
		logger.info("decodeInfos: "+decodeInfos);
		decodeInfos = URLDecoder.decode(decodeInfos, "UTF-8");
		logger.info("decodeInfos: "+decodeInfos);
		
		String[] params = decodeInfos.split("\\|");
		String name = params[0];
		logger.info(name);
		int ghao = Integer.parseInt(params[1]);
//		String phonenumberdh = params[2];
//		String phonenumber = params[3];
		String openid = params[2];
		YgMd ygMd = new YgMd();
		ygMd.setGhao(ghao);
		ygMd.setName(name);
//		if ("".equals(phonenumber) && "".equals(phonenumberdh))
//			return String.valueOf(result);
//		if (null!=phonenumber&&!"".equals(phonenumber))
//			ygMd.setPhonenumber(phonenumber);
//		if (null!=phonenumberdh&&!"".equals(phonenumberdh))
//			ygMd.setPhonenumberdh(phonenumberdh);
		
		YgMd dbYgMd = ygMdService.selectOne(ygMd);
		if(null == dbYgMd)
			return String.valueOf(result);
		
		YgInfo ygInfo = new YgInfo();
		ygInfo.setGhao(ghao);
		ygInfo.setName(name);
		ygInfo.setOpenid(openid);
		ygInfo.setDepartment(dbYgMd.getDepartment());
		
		if (ygInfoService.insert(ygInfo)) {
			result = true;
		}
		
		return String.valueOf(result);
	}
}
