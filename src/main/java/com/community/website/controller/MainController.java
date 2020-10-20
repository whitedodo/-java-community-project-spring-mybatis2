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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import com.community.website.dao.BoardDAO;
import com.community.website.dao.aop.MemberDAO;
import com.community.website.logic.Paging;
import com.community.website.service.BoardService;
import com.community.website.service.aop.MemberService;
import com.community.website.util.AESCrypto;
import com.community.website.util.HttpUtil;
import com.community.website.util.SecurityUtil;
import com.community.website.vo.BoardMultiVO;
import com.community.website.vo.BoardVO;
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
	
	@RequestMapping(value = "board/list/{boardname}", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest req, 
			  				HttpServletResponse res,
			  				@PathVariable("boardname")String boardname) {

		logger.info("Welcome Board! List(목록)");
		ModelAndView mav = new ModelAndView();

		long currentPage = 1;
		long pageSize = 10;
		long totalCount = -1;
		long startNum = -1, endNum = -1;
		
		String pageUrl = req.getContextPath() + boardURL;
        Paging paging = new Paging();
		
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
		mav.addObject("contextPath", req.getContextPath());
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
        mav.addObject("paging", paging);
		mav.addObject("pagingUrl", pageUrl + "/list/" + boardname);
		mav.addObject("boardList", listVO);
		
		mav.setViewName("main/board/list");
		
		return mav;
		
	}

	@RequestMapping(value = "board/view/{boardname}", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest req, 
			  				HttpServletResponse res,
			  				@PathVariable("boardname")String boardname) {

		logger.info("Board:: View");
		ModelAndView mav = new ModelAndView();
		long pageId = -1;
		
		if ( req.getParameter("id") != null )	
			pageId = Long.valueOf( req.getParameter("id") );
		else {
			mav.setViewName("home");
			return mav;
		}
		
		String pageUrl = req.getContextPath() + boardURL;

		BoardVO view = boardService.select(boardname, pageId);

		mav.addObject("customTitle", "게시판 - " + boardname);
		mav.addObject("contextPath", req.getContextPath());
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("pageid", pageId);
		mav.addObject("boardname", boardname);
		mav.addObject("boardView", view);
		mav.setViewName("main/board/view");
			  				
		return mav;
			  			
	}
	

	@RequestMapping(value = "board/write/{boardname}", method = RequestMethod.GET)
	public ModelAndView write(HttpServletRequest req, 
			  				HttpServletResponse res,
			  				@PathVariable("boardname")String boardname) {

		// 한글 UTF-8 처리
		res.setContentType("text/html;charset=UTF-8");
		
		try {
			req.setCharacterEncoding("UTF-8");
			
		}catch(UnsupportedEncodingException e) {
			e.getMessage();
		}
		
		logger.info("Board:: Write");
		ModelAndView mav = new ModelAndView();
		
		String pageUrl = req.getContextPath() + boardURL;
		System.out.println(req.getParameter("author"));

		mav.addObject("customTitle", "게시판 - " + boardname);
		mav.addObject("contextPath", req.getContextPath());
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
		mav.setViewName("main/board/write");
			  				
		return mav;
			  			
	}

	@RequestMapping(value = "board/write_ok/{boardname}", method = RequestMethod.POST)
	public ModelAndView write_ok(HttpServletRequest req, 
			  						 HttpServletResponse res,
			  				@PathVariable("boardname")String boardname,
			  				@RequestParam("mediaFile") MultipartFile[] files) throws IOException{
		
		logger.info("Board:: MultipartFile - Write");
		ModelAndView mav = new ModelAndView();
		
		String pageUrl = req.getContextPath() + boardURL;

		// 한글 UTF-8 처리
		res.setContentType("text/html;charset=UTF-8");
		
		try {
			req.setCharacterEncoding("UTF-8");
			
		}catch(UnsupportedEncodingException e) {
			e.getMessage();
		}
		
		// Save mediaFile on system
		List<File> fileLst = new ArrayList<File>();
		
		// Save mediaFile on system
		for (MultipartFile file : files) {
			
			if ( !file.isEmpty() && fileLst != null ) {
				fileLst = HttpUtil.saveFile(fileLst, file);
			}
		}
		
		System.out.println(fileLst);

		// 게시글 업로드 가능상태 확인
		BoardMultiVO vo = new BoardMultiVO();
		
		// 게시판명
		vo.setBoardname("sample");
		
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
		
		boardService.insertBoard(vo);

		if ( fileLst != null ) {
			
		}
		
		mav.addObject("msg", "Multiple files uploaded successfully.");
		mav.addObject("boardTitle", "게시판 글쓰기");
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
		mav.setViewName("redirect:/main/board/list/" + boardname);
			  				
		return mav;
			  			
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
	
}
