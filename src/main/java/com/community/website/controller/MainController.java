/*
 * 작성일자(Create Date): 2020-10-15
 * 프로젝트명(Project Name): Community Project
 * 저자(Author): Dodo / rabbit.white at daum dot net
 * 파일명(FileName): HomeController.java
 * 비고(Description):
 * 
 */

package com.community.website.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.community.website.dao.BoardDAO;
import com.community.website.dao.BoardDAOImpl;
import com.community.website.dao.aop.MemberDAO;
import com.community.website.logic.Paging;
import com.community.website.service.BoardService;
import com.community.website.service.aop.MemberService;
import com.community.website.util.AESCrypto;
import com.community.website.util.HttpUtil;
import com.community.website.util.SecurityUtil;
import com.community.website.vo.BoardMultiVO;
import com.community.website.vo.BoardVO;
import com.community.website.vo.FileMultiVO;
import com.community.website.vo.FileVO;
import com.community.website.vo.MemberVO;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/main")
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	@Qualifier("boardService")
    private BoardService boardService;

	@Autowired
	@Qualifier("memberService")
	private MemberService memberService;
	
	// 메시지 전송시 사용
	private String salt = "60dc26ddc7604defb7e83da1eb37dc3f";	// Temp Salt
	private String boardURL = "/main/board";
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
		
		ModelAndView mav = new ModelAndView();
		logger.info("Welcome Main Page!");
		
		List<BoardVO> vo = boardService.selectPagingAll("sample", 1, 10);

		mav.addObject("customTitle", "메인페이지");
		mav.addObject("contextPath", req.getContextPath());
		mav.addObject("boardList", vo);
		
		mav.setViewName("main/index");
		
		return mav;
	}

	@RequestMapping(value = "/member/profile.do", method = RequestMethod.GET)
	public ModelAndView profile(HttpServletRequest req, HttpServletResponse res) {
		
		ModelAndView mav = new ModelAndView();
		logger.info("Welcome Main Page!");
		
		List<BoardVO> vo = boardService.selectPagingAll("sample", 1, 10);

		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		
		mav.addObject("customTitle", "프로필 페이지");
		mav.addObject("contextPath", req.getContextPath());
		mav.addObject("memberVO", memberVO);
		mav.addObject("boardList", vo);
		
		mav.setViewName("main/member/profile");
		
		return mav;
	}

	@RequestMapping(value = "/member/profile_ok.do", method = RequestMethod.POST)
	public ModelAndView profile_ok(HttpServletRequest req, HttpServletResponse res) {
		
		ModelAndView mav = new ModelAndView();
		MemberVO vo = new MemberVO();
		AESCrypto aes = new AESCrypto();
		aes.setSalt(salt);
		
		logger.info("프로필 수정 / 삭제");
		
		int idx = -1; 
		String username = null;
		String passwd = null;
		String type = null;
		String contextPath = req.getContextPath();
		
		// 한글 UTF-8 처리
		res.setContentType("text/html;charset=UTF-8");
		
		try {
			req.setCharacterEncoding("UTF-8");
			
		}catch(UnsupportedEncodingException e) {
			e.getMessage();
		}
		
		if( req.getParameter("idx") != null ) {
			idx = Integer.valueOf( req.getParameter("idx") );
			System.out.println("번호1:" + idx);
		}
		
		if ( req.getParameter("username") != null ) {
			username = req.getParameter("username");
			System.out.println("이름1:" + username);
		}
		
		if (req.getParameter("passwd1") != null) {
			passwd = SecurityUtil.generateCustomKey(req.getParameter("passwd1"));
			System.out.println("비밀번호1:" + passwd);
		}
		
		if (req.getParameter("type") != null ) {
			type = req.getParameter("type");
			System.out.println("유형");
		}
		
		vo.setIdx(idx);
		vo.setUsername(username);
		vo.setPasswd(passwd);
		
		if ( memberService != null && type.equals("m")) {
			System.out.println("정상2");
			memberService.updateMember(req, res, vo);
			
			try {
				mav.addObject("msg", aes.Encrypt("성공적으로 수정되었습니다."));
				mav.addObject("link", aes.Encrypt("profile"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mav.setViewName("redirect:/main/member/message.do");
			
		}else if ( memberService != null && type.equals("r")) {
			
			System.out.println("정상2");
			memberService.removeMember(req, res, vo);

			try {
				mav.addObject("msg", aes.Encrypt("성공적으로 삭제되었습니다."));
				mav.addObject("link", aes.Encrypt(contextPath + "/member/logout.do"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.setViewName("redirect:/main/member/message.do");
		}
		else {
			System.out.println("널");
			mav.setViewName("main/member/profile");
		}
		
		return mav;
	}
	
	@RequestMapping(value = "board/list.do", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest req, 
			  				HttpServletResponse res) {

		logger.info("Welcome Board! List(목록)");
		ModelAndView mav = new ModelAndView();
		Paging paging = new Paging();
		String contextPath = req.getContextPath() ;
		String pageUrl = contextPath + boardURL;
		String boardname = null;
				
		long currentPage = 1;
		long pageSize = 10;
		long totalCount = -1;
		long startNum = -1, endNum = -1;
						
		if ( invaildBoardNameChecker(mav, req, res) == null)
			boardname = req.getParameter("boardname");
		else
			return invaildBoardNameChecker(mav, req, res);
		
		totalCount = boardService.selectTotalCount(boardname);
		
		if ( totalCount == -1) {
			// mav.setViewName("redirect:/myhome/main");
			mav.setViewName("home");
			return mav;
		}
		
		if ( req.getParameter("page") != null )
			currentPage = Long.valueOf( req.getParameter("page") );

        paging.setPageNo(currentPage);
        paging.setPageSize(pageSize);
        paging.setTotalCount(totalCount);

        // totalCount 호출시 생성됨.
        startNum = paging.getDbStartNum();
        endNum = paging.getDbEndNum();
        
		List<BoardVO> listVO = boardService.selectPagingAll(boardname, startNum, endNum);

		mav.addObject("customTitle", "게시판 - " + boardname);
		mav.addObject("contextPath", contextPath);
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
        mav.addObject("paging", paging);
		mav.addObject("pagingUrl", pageUrl + "/list.do?boardname=" + boardname);
		mav.addObject("boardList", listVO);
		
		mav.setViewName("main/board/list");
		
		return mav;
		
	}

	@RequestMapping(value = "board/view.do", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest req, 
			  				HttpServletResponse res) {

		logger.info("Board:: View");
		ModelAndView mav = new ModelAndView();
		String boardname = null;
		String contextPath = req.getContextPath();
		String pageUrl = contextPath + boardURL;
		long pageId = -1;

		// 게시판 이름
		if ( invaildBoardNameChecker(mav, req, res) == null)
			boardname = req.getParameter("boardname");
		else
			return invaildBoardNameChecker(mav, req, res);
		
		// 페이지 번호
		if ( req.getParameter("id") == null ) {	
			mav.setViewName("home");
			return mav;
		}
		
		pageId = Long.valueOf( req.getParameter("id") );
		boardname = req.getParameter("boardname");

		BoardVO view = boardService.select(boardname, pageId);

		FileMultiVO fileInfo = new FileMultiVO();
		fileInfo.setBoardname(boardname);
		fileInfo.setBoard_id(pageId);
		
		List<FileVO> fileList = boardService.selectFileVOBoard(fileInfo);
				
		mav.addObject("customTitle", "게시판 - " + boardname);
		mav.addObject("contextPath", contextPath);
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("pageid", pageId);
		mav.addObject("boardname", boardname);
		mav.addObject("boardView", view);
		mav.addObject("fileList", fileList);
		
		mav.setViewName("main/board/view");
			  				
		return mav;
			  			
	}
	
	@RequestMapping(value = "board/write.do", method = RequestMethod.GET)
	public ModelAndView write(HttpServletRequest req, 
			  				HttpServletResponse res) {

		logger.info("Board:: Write");
		ModelAndView mav = new ModelAndView();
		String boardname = null;
		String contextPath = req.getContextPath();
		String pageUrl = contextPath + boardURL;

		if ( invaildBoardNameChecker(mav, req, res) == null)
			boardname = req.getParameter("boardname");
		else
			return invaildBoardNameChecker(mav, req, res);
		
		// 한글 UTF-8 처리
		res.setContentType("text/html;charset=UTF-8");
		
		try {
			req.setCharacterEncoding("UTF-8");
			
		}catch(UnsupportedEncodingException e) {
			e.getMessage();
		}
		
		System.out.println(req.getParameter("author"));

		mav.addObject("customTitle", "게시판 - " + boardname);
		mav.addObject("contextPath", contextPath);
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
		mav.setViewName("main/board/write");
			  				
		return mav;
			  			
	}

	// Redirect 처리시 주소 노출 방지
	// RedirectAttributes를 선언해서 파라메터에 적용해줄 것
	@RequestMapping(value = "board/write_ok.do", method = RequestMethod.POST)
	public ModelAndView write_ok(HttpServletRequest req, 
			  						 HttpServletResponse res,
			  				@RequestParam("mediaFile") MultipartFile[] files) throws IOException{
		
		logger.info("Board:: MultipartFile - Write");
		
		ModelAndView mav = new ModelAndView();

		String boardname = null;
		String contextPath = req.getContextPath();
		String pageUrl = contextPath + boardURL;
				
		// 게시판 이름
		if ( invaildBoardNameChecker(mav, req, res) == null) {
			boardname = req.getParameter("boardname");
			System.out.println("boardname:" + boardname);
		}
		else {
			return invaildBoardNameChecker(mav, req, res);
		}
				
		List<MultipartFile> fileLst = new ArrayList<MultipartFile>();		// Save mediaFile on system

		// 한글 UTF-8 처리
		res.setContentType("text/html;charset=UTF-8");
		
		try {
			req.setCharacterEncoding("UTF-8");
			
		}catch(UnsupportedEncodingException e) {
			e.getMessage();
		}
		
		// Save mediaFile on system
		for (MultipartFile file : files) {
			
			if ( !file.isEmpty() && fileLst != null ) {
				fileLst = HttpUtil.saveFile(fileLst, file);
			}
		}
		
		// file 확장자 위반
		if ( fileLst == null ) {
			System.out.println("제한된 확장자");
			mav = invaildFileExtChecker(mav, req, null);
			return mav;			
		}
		
		//System.out.println(fileLst + "/" + fileLst.size());

		// 게시글 업로드 가능상태 확인
		BoardMultiVO vo = new BoardMultiVO();
		
		// 게시판명
		vo.setBoardname(boardname);
		
		if ( req.getParameter("name") != null)
			vo.setName(HttpUtil.getISO88591_UTF8(req.getParameter("name")));
		
		if ( req.getParameter("subject") != null)
			vo.setSubject(HttpUtil.getISO88591_UTF8(req.getParameter("subject")));
		
		if ( req.getParameter("memo") != null)
			vo.setMemo(HttpUtil.getISO88591_UTF8(req.getParameter("memo")));
		
		vo.setCount(0);
		vo.setRegidate( HttpUtil.getTodayDate() );
		vo.setIp( HttpUtil.getClientIp(req) );

		//System.out.println("subject1:" + vo.getSubject() );
		//System.out.println("subject2:" + req.getParameter("subject")) ;
		//System.out.println("subject3:" + HttpUtil.getISO88591_UTF8(req.getParameter("subject"))) ;
		//System.out.println("regidate:" + vo.getRegidate() );
		

		if ( fileLst != null ) {
			boardService.insertBoard(vo);
			BoardVO data = boardService.selectBoard(vo);
			BoardMultiVO boardInfo = new BoardMultiVO();

			boardInfo.setId(data.getId());
			boardInfo.setBoardname(boardname);
			
			insertUploadProcess(req, res, boardInfo, fileLst);
			
		}

		// System.out.println("boardname2:" + boardname);
		
		mav.addObject("msg", "Multiple files uploaded successfully.");
		mav.addObject("boardTitle", "게시판 글쓰기");
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);

		RedirectView redirectView = new RedirectView(); // redirect url 설정
		redirectView.setUrl(contextPath + "/main/board/list.do?boardname=" + boardname);
		redirectView.setExposeModelAttributes(false);

		mav.setView(redirectView);
		// 이 방식으로 선언하면, Redirect 처리할 때 파라메터가 주소에 노출됨.
		// mav.setViewName("redirect:/main/board/list.do?boardname=" + boardname);
			  				
		return mav;
			  			
	}
	
	@RequestMapping(value = "board/modify.do", method = RequestMethod.GET)
	public ModelAndView modify(HttpServletRequest req, 
			  				HttpServletResponse res) {

		logger.info("Board:: Modify");
		
		ModelAndView mav = new ModelAndView();
		String boardname = null;
		String contextPath = req.getContextPath();
		String pageUrl = contextPath + boardURL;
		long id = 1;
		
		// 한글 UTF-8 처리
		res.setContentType("text/html;charset=UTF-8");
		
		try {
			req.setCharacterEncoding("UTF-8");
			
		}catch(UnsupportedEncodingException e) {
			e.getMessage();
		}
		
		// 게시판 이름
		if ( invaildBoardNameChecker(mav, req, res) == null)
			boardname = req.getParameter("boardname");
		else
			return invaildBoardNameChecker(mav, req, res);
		
		// 페이지 번호
		if ( req.getParameter("id") == null ) {	
			mav.setViewName("home");
			return mav;
		}
		
		id = Long.valueOf( req.getParameter("id") );
		boardname = req.getParameter("boardname");
		
		BoardVO boardView = boardService.select(boardname, id);
		
		//FileVO fileView = boardService.insertFileVOBoard(vo);

		mav.addObject("customTitle", "게시판(수정) - " + boardname);
		mav.addObject("contextPath", contextPath);
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
		mav.addObject("boardView", boardView);

		mav.setViewName("main/board/modify");
			  				
		return mav;
			  			
	}
	
	@RequestMapping(value = "board/download.do", method = RequestMethod.GET)
	public void download(HttpServletRequest req, 
			  				HttpServletResponse res) {
		
		logger.info("Board:: Download");
		String contextPath = req.getContextPath();
		String boardname = null;
		long boardId = -1;
		long fileId = -1;
		
		FileMultiVO vo = new FileMultiVO();
		
		if ( req.getParameter("boardname") != null )
			boardname = req.getParameter("boardname");
		
		if ( req.getParameter("id") != null )
			boardId = Long.valueOf( req.getParameter("id") );
		
		if ( req.getParameter("fileid") != null )
			fileId = Long.valueOf( req.getParameter("fileid") );
		
		vo.setIdx(fileId);
		vo.setBoardname(boardname);
		vo.setBoard_id(boardId);
		
		FileVO fileInfo = boardService.selectFileVOInfo(vo);
		
		// HttpUtil.downloadSpring(req, res);
		boolean result = HttpUtil.download(req, res, fileInfo);
		
		// Redirect 파일
		if ( !result ) {
			
			try {
				res.sendRedirect(contextPath + "/main/board/list.do?boardname=" + boardname);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} // end of if
		
	}

	/* 
	 * 예제 - 파일 삭제 
	*/
	@RequestMapping(value = "board/download_remove.do", method = RequestMethod.GET)
	public void download_remove(HttpServletRequest req, 
			  				HttpServletResponse res) {
		
		logger.info("Board:: Download - Remove");
		String contextPath = req.getContextPath();
		String boardname = null;
		String token = null;
		long boardId = -1;
		long fileId = -1;
		
		boolean result = false;
		
		FileMultiVO vo = new FileMultiVO();
		
		if ( req.getParameter("boardname") != null )
			boardname = req.getParameter("boardname");
		
		if ( req.getParameter("id") != null )
			boardId = Long.valueOf( req.getParameter("id") );
		
		if ( req.getParameter("fileid") != null )
			fileId = Long.valueOf( req.getParameter("fileid") );
		
		if ( req.getParameter("token") != null )
			token = req.getParameter("token");
		
		vo.setIdx(fileId);
		vo.setBoardname(boardname);
		vo.setBoard_id(boardId);
		
		FileVO fileInfo = boardService.selectFileVOInfo(vo);
		
		// HttpUtil.downloadSpring(req, res);
		
		try {
			result = HttpUtil.removeFile(req, res, fileInfo);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Redirect 파일
		if ( !result ) {
			
			try {
				res.sendRedirect(contextPath + "/main/board/list.do?boardname=" + boardname);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} // end of if
		
	}
	
	@RequestMapping(value = "/member/message.do", method = RequestMethod.GET)
	public ModelAndView message(HttpServletRequest req, HttpServletResponse res){

		ModelAndView mav = new ModelAndView();
		AESCrypto aes = new AESCrypto();
		aes.setSalt(salt);
		
		String url = null;
		String msg = null;
		
		String result = "";
		
		boolean check = false;
		
		res.setContentType("text/html;charset=UTF-8");

		// 한글 UTF-8 처리
		try {
			req.setCharacterEncoding("UTF-8");
			
		}catch(UnsupportedEncodingException e) {
			e.getMessage();
		}
		
		if ( req.getParameter("link") != null )
			url = (String)req.getParameter("link");
		
		if ( req.getParameter("msg") != null)
			msg = (String)req.getParameter("msg");
		
		if ( url != null && msg != null ) {
			
			check = true; 
			
			try {
				url = aes.Decrypt(url);
				msg = aes.Decrypt(msg);
				
			} catch (Exception e) {
				e.printStackTrace();
			} // end try to catch
			
		} // end of if
		
		if ( check == true ) {
			result = "<script>alert('";
			
			result += msg;
			
			result += "');";
			
			result += "location.href = '" + url; 
			result += "';";
			
			result += "</script>";
		}
		
		mav.addObject("result", result );
		mav.setViewName("error/message");
		
		return mav;
	}
	
	private ModelAndView invaildBoardNameChecker(ModelAndView mav, HttpServletRequest req, 
									HttpServletResponse res) {
		
		String contextPath = req.getContextPath();
		AESCrypto aes = new AESCrypto();
		aes.setSalt(salt);
	
		// 게시판 이름
		if ( req.getParameter("boardname") == null) {
			
			try {
				mav.addObject("msg", aes.Encrypt("생성되지 않는 게시판입니다."));
				mav.addObject("link", aes.Encrypt(contextPath + "/main"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.setViewName("redirect:/main/member/message.do");
			
			return mav;
		}
				
		return null;
		
	}
		
	private ModelAndView invaildFileExtChecker(ModelAndView mav, 
			HttpServletRequest req, 
			Object obj) {

		String contextPath = req.getContextPath();
		String boardname = req.getParameter("boardname");
		
		System.out.println("유효성:" + boardname);
		
		AESCrypto aes = new AESCrypto();
		aes.setSalt(salt);
		
		// 게시판 이름
		if ( obj == null) {
			
			try {
				// Study 버전 (RedirectView 추가하여 URL 감출 수 있음.)
				mav.addObject("msg", aes.Encrypt("제한된 확장자입니다."));
				mav.addObject("link", aes.Encrypt(contextPath + 
						"/main/board/write.do?boardname=" + boardname));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mav.setViewName("redirect:/main/member/message.do");
			return mav;
		}
		
		return null;
	
	}

	// 메모사항: 참고로 Autowired를 HttpUtil.java에 사용하려고 하면 안 된다.
	private void insertUploadProcess(HttpServletRequest req, HttpServletResponse res,
									BoardMultiVO vo, Object obj) {
		
		@SuppressWarnings("unchecked")
		List<MultipartFile> fileLst = (List<MultipartFile>) obj;
		FileMultiVO usrFileVO = new FileMultiVO();
		
		for (MultipartFile file : fileLst) {

			// 파일명
	        String fileName = file.getOriginalFilename();
	        
			// 파일 이름 변경
		    UUID uuid = UUID.randomUUID();
		    String saveName = uuid + "_" + file.getOriginalFilename();
	        String contentType = file.getContentType();
	        long filesize = file.getSize();
	        
	        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
	        
    	    // 저장할 File 객체를 생성
        	File saveFile = new File(HttpUtil.getUploadPath(), saveName); // 저장할 폴더 이름, 저장할 파일 이름
			System.out.println("파일프로세스/파일명:" + file.getOriginalFilename());
			System.out.println("파일프로세스/게시판명:" + vo.getBoardname());
			
			usrFileVO.setBoardname(vo.getBoardname());
			usrFileVO.setRealname(fileName);
			usrFileVO.setFiledir("");
			usrFileVO.setFilename(saveName);
			usrFileVO.setFileext(ext);
			usrFileVO.setContenttype(contentType);
			usrFileVO.setFilesize(filesize);
			usrFileVO.setBoard_id(vo.getId());
			usrFileVO.setRegidate(HttpUtil.getTodayDate());
			usrFileVO.setIp(HttpUtil.getClientIp(req));
			
			try {
				file.transferTo(saveFile);
				
				// 원리는 이렇다.
				boardService.insertFileVOBoard(usrFileVO);
				
			}
			catch(IllegalStateException e) {
		        e.printStackTrace();
			}
			catch(IOException e) {
		        e.printStackTrace();
			} // end try to catch
			
		}
		
	}
	
}
