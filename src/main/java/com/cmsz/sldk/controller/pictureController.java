package com.cmsz.sldk.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cmsz.sldk.entity.vo.ResultState;
import com.cmsz.sldk.entity.vo.ResultStatusEnum;
import com.cmsz.sldk.model.YgInfo;
import com.cmsz.sldk.service.YgInfoService;

@RestController
@RequestMapping("${adminPath}/picture")
public class pictureController {
	
	protected Logger    logger = LoggerFactory.getLogger( getClass() );
	
	/**
	 * 上班打卡
	 * 此方法只返回打卡成功与否
	 * 前台在接收到成功与否后要更新数据库记录
	 */
	@Autowired
	private YgInfoService ygInfoService;
	
	public static final double Threshold = 0.50; //置信度阈值
	public static final String dockerName = "detect_container"; //容器名
	//docker路径
	public static final String classifier_path="/root/openface/demos/classifier.py"; //分类脚本
	public static final String pkl_path = "/root/openface/share/train_repre/classifier.pkl"; //分类器
	public static final String recognition_path="/root/openface/share/tmp/"; //打卡人照片存放路径，识别用
	//虚拟机路径
	public static final String local_collect_path="/var/openface/registerphoto/"; //打卡人照片存放路径，训练集
	public static final String local_recognition_path = "/var/openface/tmp/";
	/**   
     * 图片采集   
     *    
     * @param file   
     * @return   
     */    
    @RequestMapping("/collect") 
    @ResponseBody    
    public boolean collectPic(@RequestParam("file") MultipartFile file, HttpServletRequest request) {    
    	String openid  = request.getParameter("openid");
    	boolean result = false;
        YgInfo ygInfo = ygInfoService.selectByOpenid(openid);
        String saveFilePath = local_collect_path+ygInfo.getGhao()+"/";
        ResultState saveFileRes = saveFile(file, saveFilePath);
    	
        if (saveFileRes.getResult().getStatus() == ResultStatusEnum.SUCCESS.getStatus()) {
    		//采集照片成功，采集照片数量减一
        	result = CollectOne();
    	}
    	
    	return result;
    }
    
    /**   
     * 图片识别
     *    
     * @param file   
     * @return   
     */    
    @RequestMapping("/recognition")
    @ResponseBody    
    public boolean recognitionPic(@RequestParam("file") MultipartFile file, HttpServletRequest request) {    
    	String openid  = request.getParameter("openid");
    	boolean result = false;
        YgInfo ygInfo = ygInfoService.selectByOpenid(openid);
        String saveFilePath = local_recognition_path+ygInfo.getGhao()+"/";
    	ResultState saveFileRes = saveFile(file, saveFilePath);
    	
        if (saveFileRes.getResult().getStatus() == ResultStatusEnum.SUCCESS.getStatus()) {
    		//进行识别
        	String filename = saveFileRes.getData();
    		String ygrecognitionPath = recognition_path+ygInfo.getGhao()+"/"+filename;
    		logger.info("ygrecognitionPath: "+ygrecognitionPath);
    		result = recognition(ygrecognitionPath, ygInfo.getGhao());
    		logger.info("-------recognition:"+result);
    	}
    	
    	return result;
    }
    
    
    private ResultState saveFile(MultipartFile file, String saveFilePath) {
    	
    	ResultState result = new ResultState();
    	
    	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    	String filename = date.format(new Date())+".png";
    	System.out.println(filename);
    	result.setData(filename);
    	
    	if (!file.isEmpty()) {    
            try {    
                /*   
                 * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到   
                 * d:/files大家是否能实现呢？ 等等;   
                 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；   
                 * 3、文件格式; 4、文件大小的限制;   
                 */
//            	filename = "E:/pictures/"+date.format(new Date())+".png";
            	System.out.println(file.getOriginalFilename());

                BufferedOutputStream out = new BufferedOutputStream(    
                        new FileOutputStream(new File(saveFilePath+filename)));    
                out.write(file.getBytes());    
                out.flush();    
                out.close();    
            } catch (FileNotFoundException e) {    
                e.printStackTrace();    
                System.out.println("上传失败," + e.getMessage());
                result.setResult(ResultStatusEnum.ERROR);
            } catch (IOException e) {    
                e.printStackTrace();    
                System.out.println("上传失败," + e.getMessage()); 
                result.setResult(ResultStatusEnum.ERROR);
            }    
    
            System.out.println("上传成功");
            result.setResult(ResultStatusEnum.SUCCESS);
    
        } else {    
        	System.out.println("上传失败，因为文件是空的.");
        	result.setResult(ResultStatusEnum.ERROR);
        }
    	return result;
    }
    
    private boolean recognition(String ygrecognitionPath, Integer ghao) {
    	
    	boolean result = false;
    	//识别
		String[] arguments = new String[]{
				"docker","exec",dockerName,classifier_path,"infer",pkl_path,ygrecognitionPath};
		logger.info("执行识别命令：docker exec "+dockerName+" "+classifier_path+" infer "+pkl_path+" ygrecognitionPath");
		Process process;
		try {
			process = Runtime.getRuntime().exec(arguments);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			try {
				while ((line = in.readLine()) != null) {
					logger.info("recognition result: "+line);
		            if(line.contains("confidence")) {
		            	String[] answer = line.split(" ");
		            	if(ghao == Integer.parseInt(answer[1]) && Double.parseDouble(answer[3])>Threshold) {
		            		System.out.println("打卡成功");
		            		result = true;
		            	}else {
		            		System.out.println("打卡失败,请重新打卡");
		            		result = false;
		            	}
		            }
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return result;
    }
	
}
