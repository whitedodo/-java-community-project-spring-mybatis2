package com.community.website.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.community.website.dao.BoardDAO;
import com.community.website.vo.BoardMultiVO;
import com.community.website.vo.BoardVO;

@Controller
@RequestMapping("/mybatis")
public class MyBatisTestController {

	//PlatformTransactionManager 이용 수동으로 트랜잭션을 다루는 방법(MyBatis 지원)
	@Autowired
	@Qualifier("txManager")
	private PlatformTransactionManager txManager;
	DefaultTransactionDefinition def = null;
	TransactionStatus status = null;
	
	@Autowired
	@Qualifier("boardDAO")
	private BoardDAO boardDao;
	
	private static final Logger logger = LoggerFactory.getLogger(MyBatisTestController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "tx", method = RequestMethod.GET)
	public String tx() {
		logger.info("Welcome MyBatis Transaction! The client locale is");
		
		transactionSample();
		
		return "home";
	}
	
	private void transactionSample() {
		
		BoardMultiVO vo1 = new BoardMultiVO();
		vo1.setName("야호");
		vo1.setSubject("제목1");
		vo1.setMemo("내용");
		vo1.setCount(0);
		vo1.setRegidate(Timestamp.valueOf("2020-05-01 11:00:20"));
		vo1.setBoardname("sample");
		
		try {

			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

			status = txManager.getTransaction(def);	
			
			boardDao.insertBoard(vo1);
			boardDao.insertBoard(vo1);
			
		txManager.commit(status);
		
		} catch (Exception e) {
			txManager.rollback(status);
		}
		
	}
}
