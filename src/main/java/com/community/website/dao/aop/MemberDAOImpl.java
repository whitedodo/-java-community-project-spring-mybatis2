/*
 * 작성일자(Create Date): 2020-10-15
 * 프로젝트명(Project Name): Community Project
 * 저자(Author): Dodo / rabbit.white at daum dot net
 * 파일명(FileName): MemberDAOImpl.java
 * 비고(Description):
 * 
 */

package com.community.website.dao.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.community.website.vo.MemberVO;

// 심각한 문제 
// (AOP를 걸어버리면, @Repository 충돌남. (SqlSessionFactory를 불러오는 순간 무언가 충돌남)
@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	
	@Autowired
	@Qualifier("sqlSession")
    private SqlSession sqlSession;

	private static final String Namespace = "com.community.website.mapper.MemberMapper";
	
	@Override
	public MemberVO selectMember(String idx) {
		return sqlSession.selectOne(Namespace + ".select", idx);
	}
	
	@Override
	public List<MemberVO> selectAll() {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", 1);
		paramMap.put("end", 10);
		
		return sqlSession.selectList(Namespace + ".selectAll", paramMap);
		
	}

	@Override
	public long selectCount() {
		return sqlSession.selectOne(Namespace + ".selectCount");
	}
	
	@Override
	public void createMember(MemberVO vo) {

		//System.out.println("추가 결과" + sqlSession.insert(Namespace + ".insertMember", vo) );
		sqlSession.insert(Namespace + ".insertMember", vo);
	}

	@Override
	public void updateMember(MemberVO vo) {
		System.out.println("MemberDAO:" + vo.getUsername());
		sqlSession.update(Namespace + ".updateMember", vo);
		
	}

	@Override
	public void removeMember(MemberVO vo) {

		String idx = String.valueOf( vo.getIdx());
		sqlSession.delete(Namespace + ".deleteMember", idx);
		
	}

	@Override
	public MemberVO selectMailMember(MemberVO vo) {
		
		return sqlSession.selectOne(Namespace + ".selectCheckMember", vo);
		// return null;
		
	}

}
