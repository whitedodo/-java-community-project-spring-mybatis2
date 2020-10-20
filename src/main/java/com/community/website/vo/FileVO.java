package com.community.website.vo;

import java.sql.Timestamp;

public class FileVO {

	private long idx;
	private String realname;
	private String filedir;
	private String filename;
	private String fileext;
	private String contenttype;
	private long filesize;
	private long board_id;
	private Timestamp regidate;
	
	public long getIdx() {
		return idx;
	}
	public void setIdx(long idx) {
		this.idx = idx;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getFiledir() {
		return filedir;
	}
	public void setFiledir(String filedir) {
		this.filedir = filedir;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileext() {
		return fileext;
	}
	public void setFileext(String fileext) {
		this.fileext = fileext;
	}
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public long getBoard_id() {
		return board_id;
	}
	public void setBoard_id(long board_id) {
		this.board_id = board_id;
	}
	public Timestamp getRegidate() {
		return regidate;
	}
	public void setRegidate(Timestamp regidate) {
		this.regidate = regidate;
	}
	
}
