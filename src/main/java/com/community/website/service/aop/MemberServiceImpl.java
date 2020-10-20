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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.community.website.dao.aop.MemberDAO;
import com.community.website.dao.aop.MemberDAOImpl;
import com.community.website.util.HttpUtil;
import com.community.website.util.SecurityUtil;
import com.community.website.vo.MemberVO;

// 인터페이스 사용불가(AOP 적용시)
public class MemberServiceImpl implements MemberService{

	@Autowired
	@Qualifier("memberDAO")
	private MemberDAO memberDAO;
		
	public void test() {
		System.out.println("중간");
	}
	
	public int test2() {
		System.out.println("중간-정수형");
		return 2;
	}
	
	public void test3() {

		/*
		SqlMapDataSourceFactory sFactory = new SqlMapDataSourceFactory();
		DataSource ds = sFactory.dataSource();
		
		if ( ds == null) {
			System.out.println("데이터소스는 닫혔다.");
		}
		
		try {
			System.out.println("데이터소스가 열렸다.");
			System.out.println(ds.getConnection().toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	// AOP 사용할 때, boolean 사용하면 안 됨
	public int authorize(HttpServletRequest req, MemberVO vo) {
		
		MemberVO currentNode = null;
		// MemberDAO memberDAO = new MemberDAOImpl();

		String msgPack = null;
		int result = 0;
				
		long count = memberDAO.selectCount();
		currentNode = memberDAO.selectMailMember(vo);
		
		String email = null;
		String passwd = null;
		String token = null;
		String saltKey = SecurityUtil.getSaltKey();
		String usrToken = SecurityUtil.generateCustomExtKey(saltKey);
		
		// System.out.println(count);
		System.out.println("비즈니스 로그인 핵심 - 코어 구현" + count);
		
		// 1차 인증
		if ( count > 0 && currentNode != null) {
			email = currentNode.getUsername();
			passwd = currentNode.getPasswd();
			
			System.out.println("비밀번호:" + passwd + "/" + vo.getPasswd());
			
			result = 1;			// 비밀번호 일치 확인
		}
		
		// 신규 관리자 계정 생성
		if (count == 0) {
			MemberVO node = new MemberVO();
			
			node.setUsername("rabbit.white@localhost.com");
			msgPack = SecurityUtil.generateCustomKey("123456");
						
			node.setPasswd(msgPack);
			node.setRegidate( HttpUtil.getTodayDate() );
			node.setIp( HttpUtil.getClientIp(req) );

			System.out.println("계정 생성");
			// DB 초기 계정 생성하기
			memberDAO.createMember(node);
			
			result = 2;			// 신규 계정 생성(계정 일치 아님)
		}

		
		if ( req.getParameter("token") != null )
			token = req.getParameter("token");
		
		System.out.println("2단계A?:");
		System.out.println("aroundMethod 호출 1");
		
		boolean crosscheck = SecurityUtil.compareCustomExtCheck(SecurityUtil.generateCustomExtKey(usrToken), token);
		// 원래의 메소드를 호출한다.

		System.out.println("크로스 체크:" + crosscheck);
		//System.out.println("암호:" + currentNode.getPasswd());
		System.out.println("결과:" + result);
		
		// 2차 - 세션 인증 
		if ( result == 1 && crosscheck == true ) {
			
			HttpSession session = req.getSession(true);
			session.setAttribute("login", token);
			session.setAttribute("memberVO", currentNode);
			session.setMaxInactiveInterval(60 * 60);		// 유효시간
			
		}
		
		// System.out.println("이메일:" + email);
		
		return result;
		
	}

	@Override
	public int updateMember(HttpServletRequest req, HttpServletResponse res, MemberVO vo) {
		memberDAO.updateMember(vo);
		
		return 0;
	}

	@Override
	public int removeMember(HttpServletRequest req, HttpServletResponse res, MemberVO vo) {
		memberDAO.removeMember(vo);
		
		return 0;
	}
	
}
