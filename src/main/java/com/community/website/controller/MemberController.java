/*
 * 작성일자(Create Date): 2020-10-15
 * 프로젝트명(Project Name): Community Project
 * 저자(Author): Dodo / rabbit.white at daum dot net
 * 파일명(FileName): HomeController.java
 * 비고(Description):
 * 
 */

package com.community.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.community.website.service.aop.MemberService;
import com.community.website.util.SecurityUtil;
import com.community.website.vo.MemberVO;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private final String tokenKey = SecurityUtil.getSaltKey();
	
	// AOP로 맴버 서비스 콜할 때 충돌남 (편리한 이면에 오류들)
	@Autowired
	@Qualifier("memberService")
	private MemberService memberAOP;
	
	// 로그인 부분
	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public ModelAndView login(HttpServletResponse res,
							 HttpServletRequest req) {
		
		logger.info("Welcome login!");
	
		ModelAndView mav = new ModelAndView();
		String usrToken = SecurityUtil.generateCustomExtKey(tokenKey);

		//boolean result = SecurityUtil.compareCustomExtCheck(SecurityUtil.generateCustomExtKey(tokenKey), usrToken); 
		//System.out.println("암호키 검증완료:" + result);
		//mav.addObject("pageTitle", "MyHome");
		mav.addObject("contextPath", req.getContextPath());
		mav.addObject("token", usrToken);
				
		mav.setViewName("/sign-in/login");
		return mav;
	}
	
	// 로그인 처리
	@RequestMapping(value = "authorize.do", method = RequestMethod.POST)
	public ModelAndView authorize(HttpServletResponse res,
									HttpServletRequest req) {
		
		logger.info("Authorize - MyHome Communities");
	
		MemberVO memberVO = new MemberVO();
		ModelAndView mav = new ModelAndView();
		
		String username = null;
		String passwd = null;
		String token = null;
		
		if ( req.getParameter("username") != null )
			username = req.getParameter("username");
		
		if ( req.getParameter("passwd") != null )
			passwd = req.getParameter("passwd");
		
		if ( req.getParameter("token") != null )
			token = req.getParameter("token");
		
		memberVO.setUsername(req.getParameter("username"));
		memberVO.setPasswd(SecurityUtil.generateCustomKey( req.getParameter("passwd") ) );
		 
		int result = memberAOP.authorize(req, memberVO);
		// factory.close();
		
		mav.addObject("contextPath", req.getContextPath());
		
		// 로그인 (성공: 1번, 실패: 0번, 생성: 2번)
		if ( result == 1 ) {
			
			mav.setViewName("redirect:/");
			
		}else if (result == 2) {
			
			mav.setViewName("redirect:/member/login.do");
			
		}else {
			
			mav.setViewName("redirect:/member/login.do");
			
		}
		
		return mav;
	}
	
	// 로그아웃 부분
	@RequestMapping(value = "logout.do")
    public String logout(HttpSession session) {
		
        session.invalidate(); 				// 세션 초기화
        return "redirect:/member/login.do"; 	// 로그아웃 후 로그인화면으로 이동
        
    }
	
}
