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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cmsz.sldk.entity.vo.ResultState;
import com.cmsz.sldk.entity.vo.ResultStatusEnum;
import com.cmsz.sldk.model.CollectState;
import com.cmsz.sldk.model.DkRecord;
import com.cmsz.sldk.model.YgInfo;
import com.cmsz.sldk.service.CollectStateService;
import com.cmsz.sldk.service.DkRecordService;
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
	
	@Autowired
	private CollectStateService collectStateService;
	
	@Autowired
	private DkRecordService dkRecordService;
	
	public static final double Threshold = 0.001; //置信度阈值
	public static final String dockerName = "detect_container"; //容器名
	//docker路径
	public static final String classifier_path="/root/openface/demos/classifier.py"; //分类脚本
	public static final String pkl_path = "/root/openface/share/train_repre/classifier.pkl"; //分类器
	public static final String recognition_path="/root/openface/share/tmp/"; //打卡人照片存放路径，识别用
	//虚拟机路径
	public static final String local_collect_path="/var/openface/registerphoto/"; //打卡人照片存放路径，训练集
	public static final String local_recognition_path = "/var/openface/tmp/";
	public static final String local_success_bak = "/var/openface/success/";
	public static final String local_fail_bak = "/var/openface/fail/";
	
	public static final Integer MAX_NUM = 20;
	
	@ResponseBody
	@RequestMapping("/iscollected/{openid}")
	public boolean isCollected(@PathVariable("openid") String openid) {
		boolean result = false;
		
		CollectState collectState = collectStateService.selectByOpenid(openid);
		if (null == collectState)
			return false;
		int currentNum = collectState.getCurrentNum();
		int maxNum = collectState.getMaxNum();
		
		if (currentNum >= maxNum)
			result = true;
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/neednum/{openid}")
	public Integer needNum(@PathVariable("openid") String openid) {
		
		CollectState collectState = collectStateService.selectByOpenid(openid);
		if (null == collectState)
			return MAX_NUM;
		int currentNum = collectState.getCurrentNum();
		int maxNum = collectState.getMaxNum();
		
		return (maxNum-currentNum);
	}
	
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

        ResultState saveFileRes = saveFile(file, saveFilePath, ygInfo.getGhao());
    	
        if (saveFileRes.getResult().getStatus() == ResultStatusEnum.SUCCESS.getStatus()) {
    		//采集照片成功，采集照片数量减一
        	if (collectStateService.isExist(openid)) {
        		CollectState collectState = collectStateService.selectByOpenid(openid);
        		int currentNum = collectState.getCurrentNum() + 1;
        		result = collectStateService.updateCollectState(openid, currentNum);
        	} else {
        		CollectState collectState = new CollectState();
        		collectState.setCurrentNum(1);
        		collectState.setOpenid(openid);
        		collectState.setGhao(ygInfo.getGhao());
        		collectState.setMaxNum(MAX_NUM);
        		result = collectStateService.insert(collectState);
        	}
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
    	ResultState saveFileRes = saveFile(file, saveFilePath, ygInfo.getGhao());
    	
        if (saveFileRes.getResult().getStatus() == ResultStatusEnum.SUCCESS.getStatus()) {
    		//进行识别
        	String filename = saveFileRes.getData();
    		String ygrecognitionPath = recognition_path+ygInfo.getGhao()+"/"+filename;
    		logger.info("ygrecognitionPath: "+ygrecognitionPath);
    		result = recognition(ygrecognitionPath, ygInfo.getGhao());
    		logger.info("-------recognition:"+result);
    		
    		String source_file = saveFilePath + filename; //经过打卡识别的照片
    		String dest_path;//备份路径
    		if(result==true) {
    			dest_path = local_success_bak + ygInfo.getGhao()+"/";
    			//打卡成功，保存打卡记录
    			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			String dkTime = date.format(new Date());
    			DkRecord dkRecord = new DkRecord();
    			dkRecord.setDkTime(dkTime);
    			dkRecord.setOpenid(openid);
    			dkRecordService.insert(dkRecord);
    		}else {
    			dest_path = local_fail_bak + ygInfo.getGhao()+"/";
    			//打卡成功，保存打卡记录
    			System.out.println("--------------------------------false: ---");
    			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			String dkTime = date.format(new Date());
    			DkRecord dkRecord = new DkRecord();
    			dkRecord.setDkTime(dkTime);
    			dkRecord.setOpenid(openid);
    			dkRecordService.insert(dkRecord);
    		}
    		filebak(source_file, dest_path);//备份文件
    	}
    	
    	return result;
    }
    
    
    private ResultState saveFile(MultipartFile file, String saveFilePath, Integer ghao) {
    	
    	ResultState result = new ResultState();
    	
    	SimpleDateFormat date = new SimpleDateFormat("-yyyyMMdd-HHmmss");
    	String filename = ghao+date.format(new Date())+".png";
    	System.out.println(filename);
    	result.setData(filename);
    	
    	if (!file.isEmpty()) {    
            try {
            	dir_exist(saveFilePath);//判断目标文件夹是否存在，不存在即创建

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
		logger.info("执行识别命令：docker exec "+dockerName+" "+classifier_path+" infer "+pkl_path+" "+ygrecognitionPath);
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
    
    //传入源文件全路径和目标路径的文件夹路径
    private boolean filebak(String fromPath, String toPath) {
    	File fromFile = new File(fromPath);
    	File toFilePath = new File(toPath);
    	if(toFilePath.exists()) {
    		if(toFilePath.isFile()) {
    			logger.error("目标路径是个文件，请检查目标路径");
    			return false;
    		}
    	}else {
    		if(!toFilePath.mkdirs()) {
    			logger.error("目标路径文件夹不存在，创建失败");
    			return false;
    		}
    	}
    	
    	if(fromFile.isFile() && fromFile.exists()) {
    		String toFile = toPath + "/" + fromFile.getName();
    		if(!fromFile.renameTo(new File(toFile))) {
    			logger.error("备份文件失败，请检查操作权限等因素");
    			return false;
    		}
    	}else {
    		logger.error("要备份的文件不存在，或者路径有问题，请检查");
    		return false;
    	}
    	
    	logger.info("已成功移动文件"+fromFile.getName()+"到"+toPath+"下");
        return true;
    }
    
    
    //判断文件目录是否存在方法
    private boolean dir_exist(String dir) {
    	File dirFile = new File(dir);
     	if(dirFile.exists()) {
    		if(dirFile.isFile()) {
    			logger.error("目标路径是个文件，请检查目标路径");
    			return false;
    		}
    	}else {
    		if(!dirFile.mkdirs()) {
    			logger.error("目标路径文件夹不存在，创建失败");
    			return false;
    		}
    	}
     	return true;
    }
	
}
