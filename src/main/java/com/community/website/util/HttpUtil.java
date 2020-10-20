package com.community.website.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class HttpUtil {
	
	// 윈도우 스타일
	private final static String UPLOAD_PATH = "c:" + File.separator + "temp" + File.separator;
	
	// 리눅스 스타일
	//private final String UPLOAD_PATH = "/home/user1/storage/v1/upload/";
	
	private final static String[] fileRestrictExt = {"jpg", "png", "gif"}; 
	
	public static String getClientIp(HttpServletRequest req) {
		 
	    String[] header_IPs = {
	                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED"
	                , "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED"
	                , "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"
        } ;
	     
	    for (String header : header_IPs) {
	        String ip = req.getHeader(header);
	         
	        if (ip != null && !"unknown".equalsIgnoreCase(ip) && ip.length() != 0) {
	            return ip;
	        } // end of if
	        
	    } // end of for
	     
	    return req.getRemoteAddr() ;
	     
	}
	
	public static Timestamp getTodayDate() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = formatter.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		
		return ts;
		
	}
	
	public static List<File> saveFile(List<File> lstFile, MultipartFile file) throws IOException{
				
		boolean status = false;
		
		// 파일 이름 변경
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid + "_" + file.getOriginalFilename();
	
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long filesize = file.getSize();
        
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
	    
        // 파일 제한 확장자
        for ( String val : fileRestrictExt ) {
        	
        	if ( val.equals(ext) == true ) {
        		status = true;
        	}
        	
        }
        
        if ( status == true ) {
        	
    	    // 저장할 File 객체를 생성
        	File saveFile = new File(UPLOAD_PATH, saveName); // 저장할 폴더 이름, 저장할 파일 이름
        	lstFile.add(saveFile);
        	
    	    return lstFile;
    	    
        }else {
        	return null;
        }
	    
	}
	
	public static int saveTransfer(MultipartFile file, File saveFile) {
		
	    try {
	        file.transferTo(saveFile);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return 0;
		
	}
	
	public static String getISO88591_UTF8(String val) {
		try {
			return new String(val.getBytes("8859_1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
