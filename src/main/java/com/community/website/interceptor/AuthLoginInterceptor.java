package com.community.website.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.community.website.util.SecurityUtil;

//로그인처리를 담당하는 인터셉터
public class AuthLoginInterceptor extends HandlerInterceptorAdapter{

	// preHandle() : 컨트롤러보다 먼저 수행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, 
							Object handler)
		throws Exception {

			HttpSession session = req.getSession();
			Object obj = session.getAttribute("login");
			
			boolean crosscheck = false;
			String token = (String)obj;
			String saltKey = SecurityUtil.getSaltKey();
			String usrToken = SecurityUtil.generateCustomExtKey(saltKey);
			
			if ( obj != null )
			{ 
				crosscheck = SecurityUtil.compareCustomExtCheck(SecurityUtil.generateCustomExtKey(usrToken), token);
			}
			
			System.out.println("인터셉터 가동중(전단계)" + obj);
			System.out.println("암호체크:" + crosscheck);
			
			String loginUrl = req.getContextPath() + "/member/login.do";
			
			// 로그인 실패
			if ( crosscheck == false ){
				res.sendRedirect(loginUrl);	// login.do
				return false;
			}
 
			// 로그인 성공
			return true;
	}

	// 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메서드
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,
							Object handler, ModelAndView mav) throws Exception {

		System.out.println("인터셉터 가동중(후 단계)");
		// TODO Auto-generated method stub
		super.postHandle(req, res, handler, mav);
	}
	
}