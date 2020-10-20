/*
 * 작성일자(Create Date): 2020-10-15
 * 프로젝트명(Project Name): Community Project
 * 저자(Author): Dodo / rabbit.white at daum dot net
 * 파일명(FileName): MemberService.java
 * 비고(Description):
 * 
 */

package com.community.website.service.aop;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.community.website.dao.aop.MemberDAO;
import com.community.website.dao.aop.MemberDAOImpl;
import com.community.website.util.SecurityUtil;
import com.community.website.vo.MemberVO;

// 인터페이스 사용불가(AOP 적용시)
public interface MemberService {

	// AOP 사용할 때, boolean 사용하면 안 됨
	public int authorize(HttpServletRequest req, MemberVO vo);
	
	// 회원 계정 수정
	public int updateMember(HttpServletRequest req, 
							HttpServletResponse res, MemberVO vo);
	
	// 회원 계정 삭제
	public int removeMember(HttpServletRequest req, 
							HttpServletResponse res, MemberVO vo);
	
}
