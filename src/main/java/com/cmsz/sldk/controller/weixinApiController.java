package com.cmsz.sldk.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoleilu.hutool.http.HttpRequest;
import com.xiaoleilu.hutool.http.HttpResponse;
import com.xiaoleilu.hutool.json.JSONObject;

@RestController
@RequestMapping("${adminPath}/weixin")
public class weixinApiController  {

	protected Logger    logger = LoggerFactory.getLogger( getClass() );
	
	@Value("${weixin.baseinfo.appid}")
	private String appid;
	
	@Value("${weixin.baseinfo.secret}")
	private String secret;
	
	@Value("${weixin.EARTH_RADIUS}")
	private double EARTH_RADIUS;
	
	@Value("${weixin.GT_LATITUDE}")
	private double GT_LATITUDE;
	
	@Value("${weixin.GT_LONGITUDE}")
	private double GT_LONGITUDE;
	
	@Value("${weixin.GT_DISTANCE}")
	private double GT_DISTANCE;
	
	/**
	 * 获取openid
	 * @param code	微信官方文档，获取openid时所用code
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/openid")
	public String openidInfo(String code, HttpServletRequest request) {
		
		logger.info("code: "+code);
		//http://localhost:8013/ghlp/weixin/openid?code=013XJPlf1iWQfy0b4onf1bQNlf1XJPl5
		String openid = null;
		String jscode2session = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		jscode2session = jscode2session.replace("APPID", appid).replace("SECRET",secret).replace("JSCODE", code);
		logger.info(jscode2session);
		
		HttpRequest post = HttpRequest.post( jscode2session );
        post.charset( "UTF-8" );
        HttpResponse resp = post.execute();
        if (!(resp.getStatus() != 200 || !"0"
                .equals( new JSONObject( resp.body() ).getStr( "status", "1" ) ))) {
        	logger.info("error");
        }
		openid = resp.body();
		//限制请求一定获取到openid
//		{ "access_token":"ACCESS_TOKEN",
//		"expires_in":7200,
//		"refresh_token":"REFRESH_TOKEN",
//		"openid":"OPENID",
//		"scope":"SCOPE" }
		logger.info("openid result: "+openid);
		return openid;
	}
	
	/**
	 * 获取活动报名小程序码
	 * @param hdid	活动ID
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/getwxacodeunlimit/{page}/{hdid}")
	public byte[] getwxacodeunlimit(@PathVariable("page") String page, @PathVariable("hdid") String hdid, 
			HttpServletRequest request) throws UnsupportedEncodingException {
		logger.info(page+"------"+hdid);
		page = page.replace('.', '/');
		if (hdid.contains("-"))
			hdid = hdid.substring(0,hdid.indexOf('-'));
		
		String gettoken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		gettoken = gettoken.replace("APPID", appid).replace("APPSECRET", secret);
		HttpRequest get = HttpRequest.get( gettoken );
        get.charset( "UTF-8" );
        HttpResponse resp = get.execute();
        if (resp.getStatus() != 200) {
        	System.out.println("error");
        }
        logger.info(resp.body());
        JSONObject ac = new JSONObject( resp.body() );
        String access_token = ac.getStr("access_token");
        logger.info(access_token);
        
        String getwxacodeunlimit = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
		getwxacodeunlimit = getwxacodeunlimit.replace("ACCESS_TOKEN", access_token);
		HttpRequest post2 = HttpRequest.post( getwxacodeunlimit );
		post2.charset( "UTF-8" );
		Map<String, Object> jsonMap = new HashMap<String,Object>();
		String scene = hdid;
		logger.info("scene: "+scene);
		jsonMap.put("scene", scene);
		//报名小程序码："page/component/pages/signupinfo/signupinfo"
		//签到小程序码："page/component/pages/sign-info/sign-info"
		jsonMap.put("page", page);
		jsonMap.put("width", 430);
		jsonMap.put("auto_color", true);
		post2.body( new JSONObject( jsonMap ) );
		logger.info(getwxacodeunlimit+"\n"+ new JSONObject( jsonMap ));
        HttpResponse resp2 = post2.execute();
        if (resp2.getStatus() != 200) {
        	logger.info("error");
        }
        byte[] rb = resp2.bodyBytes();
        
        return rb;
	}
	
	@ResponseBody
	@RequestMapping("/distance/{openid}/{latitude}/{longitude}")
	public String distance(@PathVariable("openid") String openid, 
			@PathVariable("latitude") Double latitude, 
			@PathVariable("longitude") Double longitude, HttpServletRequest request) {
		
		boolean result = false;
		logger.info("openid: "+openid);
		logger.info("latitude: "+latitude);
		logger.info("longitude: "+longitude);
		logger.info("GT_LATITUDE:"+GT_LATITUDE);
		logger.info("GT_LONGITUDE:"+GT_LONGITUDE);
		logger.info("EARTH_RADIUS:"+EARTH_RADIUS);
		
		double dis = getDistance(latitude, longitude, GT_LATITUDE, GT_LONGITUDE);
		logger.info(dis+"");
		if (dis <= GT_DISTANCE) {
			result = true;
		}
			
		return String.valueOf(result);
	}
	
	/**
     * 将二进制转换为图片
     * 
     * @param base64String
     */ 
    static void base64StringToImage(byte[] bytes1) { 
        try { 
   
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1); 
            BufferedImage bi1 = ImageIO.read(bais); 
            File w2 = new File("e://QQ.jpg");// 可以是jpg,png,gif格式 
            ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
    
 // 二进制转字符串  
    public static String byte2hex(byte[] b)   
    {  
       StringBuffer sb = new StringBuffer();  
       String tmp = "";  
       for (int i = 0; i < b.length; i++) {  
        tmp = Integer.toHexString(b[i] & 0XFF);  
        if (tmp.length() == 1){  
            sb.append("0" + tmp);  
        }else{  
            sb.append(tmp);  
        }  
          
       }  
       return sb.toString();  
    }
    
    private static double rad(double d) { 
        return d * Math.PI / 180.0; 
    }
      
    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @param lat1 用户经度
     * @param lng1 用户纬度
     * @param lat2 商家经度
     * @param lng2 商家纬度
     * @return
     */
    public double getDistance(double lat1, double lng1, double lat2, double lng2) {
        
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
//        distance = Math.round(distance * 10000) / 10000;
        
        return distance;
    }
}
