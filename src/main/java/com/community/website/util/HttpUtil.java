package com.community.website.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.community.website.controller.MainController;
import com.community.website.dao.BoardDAO;
import com.community.website.dao.BoardDAOImpl;
import com.community.website.service.BoardService;
import com.community.website.service.BoardServiceImpl;
import com.community.website.vo.BoardMultiVO;
import com.community.website.vo.FileMultiVO;
import com.community.website.vo.FileVO;

public class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	// 윈도우 스타일
	private final static String UPLOAD_PATH = "c:" + File.separator + "temp" + File.separator;
	
	// 리눅스 스타일
	//private final String UPLOAD_PATH = "/home/user1/storage/v1/upload/";
	
	private final static String[] fileRestrictExt = {"jpg", "png", "gif"}; 
	
	public static String getClientIp(HttpServletRequest req) {
		 
		 String ip = req.getHeader("X-Forwarded-For");
		    logger.info("> X-FORWARDED-FOR : " + ip);

		    if (ip == null) {
		        ip = req.getHeader("Proxy-Client-IP");
		        logger.info("> Proxy-Client-IP : " + ip);
		    }
		    if (ip == null) {
		        ip = req.getHeader("WL-Proxy-Client-IP");
		        logger.info(">  WL-Proxy-Client-IP : " + ip);
		    }
		    if (ip == null) {
		        ip = req.getHeader("HTTP_CLIENT_IP");
		        logger.info("> HTTP_CLIENT_IP : " + ip);
		    }
		    if (ip == null) {
		        ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		        logger.info("> HTTP_X_FORWARDED_FOR : " + ip);
		    }
		    if (ip == null) {
		        ip = req.getRemoteAddr();
		        logger.info("> getRemoteAddr : "+ip);
		    }
		    logger.info("> Result : IP Address : " + ip);

		    return ip;
	     
	}
	
	public static Timestamp getTodayDate() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = formatter.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		
		return ts;
		
	}
	
	public static String getUploadPath() {
		return UPLOAD_PATH;
	}
	
	public static List<MultipartFile> saveFile(List<MultipartFile> lstFile, MultipartFile file) throws IOException{
				
		boolean status = false;
		
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
	    
        // 파일 제한 확장자
        for ( String val : fileRestrictExt ) {
        	
        	if ( val.equals(ext) == true ) {
        		status = true;
        	}
        }
        
        if ( status == true ) {
        	lstFile.add(file);
    	    return lstFile;
    	    
        }else {
        	return null;
        }
	    
	}
	
	public static int saveTransfer(MultipartFile file, File saveFile) {
		
	    try {
	        file.transferTo(saveFile);
	    }
	    
		catch(IllegalStateException e) {
		    e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return 0;
		
	}
		
	// 이전 버전(JSP 호환 버전)
	public static boolean download(HttpServletRequest req, 
								HttpServletResponse res,
								FileVO vo) {
		
		boolean status = true;
		
		// 한글 전송(UTF-8)
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		 
	    // 파일 업로드된 경로
	    // String root = req.getSession().getServletContext().getRealPath("/");
		String root = UPLOAD_PATH;
		String savePath = root; 
	    //String savePath = root + "upload";
	 
	    // 서버에 실제 저장된 파일명
	    //String filename = "6899c620-031a-4ff7-89cd-ca2e1cbeee21_flow_key_039.jpg";
		String filename = vo.getFilename(); 
		
	    // 실제 내보낼 파일명
	    //String orgfilename = "테스트.jpg" ;
	    String orgfilename = vo.getRealname();  
	    
	    InputStream in = null;
	    OutputStream os = null;
	    File file = null;
	    boolean skip = false;
	    String client = "";
	 
	    try{
	         
	        // 파일을 읽어 스트림에 담기
	        try{
	            file = new File(savePath, filename);
	            in = new FileInputStream(file);
	        }catch(FileNotFoundException fe){
	            skip = true;
	        }
	        
	        // 파일이 존재 안할 때
	        if ( !file.isFile() )
	        	return false;
	             
	        client = req.getHeader("User-Agent");
	 
	        // 파일 다운로드 헤더 지정
	        res.reset() ;
	        res.setContentType("application/octet-stream");
	        res.setHeader("Content-Description", "My Generated Data");
	 
	        if(!skip){
	 
	            // IE
	            if(client.indexOf("MSIE") != -1){
	                res.setHeader ("Content-Disposition", "attachment; filename=" + new String(orgfilename.getBytes("KSC5601"),"ISO8859_1"));
	 
	            }else{
	                // 한글 파일명 처리
	                orgfilename = new String(orgfilename.getBytes("utf-8"),"iso-8859-1");
	 
	                res.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
	                res.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
	            } 
	             
	            res.setHeader ("Content-Length", ""+file.length() );
	            os = res.getOutputStream();
	            
	            byte b[] = new byte[(int)file.length()];
	            int leng = 0;
	             
	            while( (leng = in.read(b)) > 0 ){
	                os.write(b, 0, leng);
	            }
	 
	        }else{
	            res.setContentType("text/html;charset=UTF-8");
	            // out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
	            status = false;
	        }
	         
	        in.close();
	        os.close();
	 
	    }catch(Exception e){
	      //e.printStackTrace();
	      status = false;
	    }
	    
	    return status;
		
	}

	// Spring Style - Download
	public static boolean downloadSpring(HttpServletRequest req, 
									HttpServletResponse res,
									FileVO vo) {

		boolean status = true;

		// 한글 전송(UTF-8)
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		
	    // 파일 업로드된 경로
	    // String root = req.getSession().getServletContext().getRealPath("/");
		String root = HttpUtil.getUploadPath();
		String savePath = root; 
		
		// 서버에 실제 저장된 파일명
	    //String filename = "6899c620-031a-4ff7-89cd-ca2e1cbeee21_flow_key_039.jpg";
		String filename = vo.getFilename(); 
		
	    // 실제 내보낼 파일명
	    //String orgfilename = "테스트.jpg" ;
	    String orgfilename = vo.getRealname();
	    
	    String contentType = "application/octet-stream; charset=utf-8";
	   
        String client = req.getHeader("User-Agent");
        // contentType 가져오고
 
        File file = new File(savePath, filename);
        long fileLength = file.length();
        
        if ( !file.isFile() ){
        	return false;
        }

        // 한글 파일명 처리
        try {
			orgfilename = new String(orgfilename.getBytes("utf-8"),"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        res.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
        res.setHeader("Content-Type", contentType);        
        res.setHeader("Content-Transfer-Encoding", "binary"); 
        res.setHeader("Content-Length", "" + fileLength);
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
        
        // 그 정보들을 가지고 reponse의 Header에 세팅한 후
        try (FileInputStream fis = new FileInputStream(file);
        		OutputStream out = res.getOutputStream();) {
            // saveFileName을 파라미터로 넣어 inputStream 객체를 만들고 
            // response에서 파일을 내보낼 OutputStream을 가져와서  
            int readCount = 0;
            byte[] buffer = new byte[1024];
            // 파일 읽을 만큼 크기의 buffer를 생성한 후 
            while ((readCount = fis.read(buffer)) != -1) {
                out.write(buffer, 0, readCount);
                // outputStream에 씌워준다
            }
        } catch (Exception ex) {
        	status = false;
            // throw new RuntimeException("file Load Error");
        }
		
        return status;
        
	}
	

	/*
	 *  파일 삭제, 폴더 삭제
	 */
	public static boolean removeFile(HttpServletRequest req, HttpServletResponse res,
								FileVO vo) 
			throws ServletException, IOException{
		
		// 파일 업로드된 경로
		// String root = req.getSession().getServletContext().getRealPath("/");

		// String savePath = root + UPLOAD_FOLDER + File.separator + UPLOAD_FOLDER ;
		String savePath = UPLOAD_PATH;
		// String filename = "1601561525229" ;
		String filename = vo.getFilename();
		
        File file = new File(savePath, filename);
        
        System.out.println(filename);

        // 파일이 존재할 떄
        if (file.exists()) {
        	file.delete();
        }else {
        	// 파일이 존재하지 않다면, 
        	System.out.println("파일 없어요");
        	return false;
        }
        
        removeDirectory(req, res, savePath);
        return true;
        
	}

	/*
	 * 
	 */
	private static boolean removeDirectory(HttpServletRequest req, HttpServletResponse res,

			String path) throws ServletException, IOException {

		boolean result = false;
		File usrDir = new File(path);

		if(!usrDir.exists()) {                 		// 경로 존재 여부
            result = false;     	
        }
		else {
	        File[] lowFiles = usrDir.listFiles();     	// 경로 내의 파일 리스트

	        // 폴더 삭제
	        if ( usrDir.isDirectory() 
	        		&& lowFiles.length == 0 ) {

	        	System.out.println("폴더 삭제처리 완료");
	        	usrDir.delete();
	        	return true;

	    	}else{
	    		result = false;
	    	}

        }

		return result;

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
