/*
 * 작성일자(Create Date): 2020-10-15
 * 프로젝트명(Project Name): Community Project
 * 저자(Author): Dodo / rabbit.white at daum dot net
 * 파일명(FileName): BoardVO.java
 * 비고(Description):
 * 
 */

package com.community.website.vo;

import java.sql.Timestamp;

public class BoardVO {
	
	private long id;
	private String name;
	private String subject;
	private String memo;
	private long count;
	private Timestamp regidate;
	private String ip;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Timestamp getRegidate() {
		return regidate;
	}
	public void setRegidate(Timestamp regidate) {
		this.regidate = regidate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
