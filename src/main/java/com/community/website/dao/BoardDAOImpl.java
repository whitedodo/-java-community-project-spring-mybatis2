/*
 * 작성일자(Create Date): 2020-10-15
 * 프로젝트명(Project Name): Community Project
 * 저자(Author): Dodo / rabbit.white at daum dot net
 * 파일명(FileName): BoardDAOImpl.java
 * 비고(Description):
 * 
 */

package com.community.website.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.community.website.vo.BoardMultiVO;
import com.community.website.vo.BoardVO;
import com.community.website.vo.FileMultiVO;
import com.community.website.vo.FileVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO{
	
	@Autowired
	@Qualifier("sqlSession")
    private SqlSession sqlSession; 
    
    private static final String Namespace = "com.community.website.mapper.BoardMapper";
	
	@Override
	public BoardVO selectBoard(@Param("boardname")String boardname, 
								@Param("id")long id) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardname", boardname);
		paramMap.put("id", id);
						
		return sqlSession.selectOne(Namespace + ".selectBoard", paramMap);
		
	}

	@Override
	public BoardVO selectBoard(BoardMultiVO vo) {
		
		System.out.println("셀렉트:" + vo.getRegidate());
		return sqlSession.selectOne(Namespace + ".selectBoardIdentify", vo);
	}
	
	@Override
	public long selectTotalCount(String boardname) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardname", boardname);
		
		try {
			return sqlSession.selectOne(Namespace + ".selectBoardCnt", paramMap);
		}
		catch(Exception e) {
			return -1;
		}
		
	}

	@Override
	public List<BoardVO> selectPagingAll(String boardname, long start, long end) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardname", boardname);
		paramMap.put("start", start);
		paramMap.put("end", end);
		
		return sqlSession.selectList(Namespace + ".selectPagingBoard", paramMap);
	}

	@Override
	public List<BoardVO> selectAll() {
		
		return sqlSession.selectList(Namespace + ".selectAll");
	}

	@Override
	public void insertSample(String boardname) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		BoardMultiVO vo = new BoardMultiVO();
		
		vo.setBoardname(boardname);
		vo.setId(1);
		vo.setName("홍길동");
		vo.setSubject("제목입니다.");
		vo.setMemo("야호");
		vo.setRegidate(Timestamp.valueOf("2020-01-01 01:03:05"));
		
		sqlSession.insert(Namespace + ".insertSample", vo);
		
		// 약 10만개
		for ( int i = 0; i < 2000; i++ ) {

			for (int j = 0; j < 50; j++) {
							
				
			}
			
		}
	}
	
	@Override
	public void insertBoard(BoardMultiVO vo) {
		
		sqlSession.insert(Namespace + ".insertBoard", vo);
		
	}

	@Override
	public void insertFileVOBoard(FileMultiVO vo) {
		
		if (sqlSession != null )
			sqlSession.insert(Namespace + ".insertBoardFileVO", vo);
		else {
			System.out.println("널");
		}
	}

	@Override
	public void updateBoard(BoardVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBoard(String idx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FileVO> selectFileVOBoard(FileMultiVO vo) {
		
		return sqlSession.selectList(Namespace + ".selectFileInfo", vo);
		
	}

	@Override
	public FileVO selectFileVOInfo(FileMultiVO vo) {
		return sqlSession.selectOne(Namespace + ".selectFileDownInfo", vo);
	}
	
}
