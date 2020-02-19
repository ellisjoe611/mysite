package com.douzone.mysite.vo;

public class BoardVO {
	private Long no;
	private String title;
	private String contents;
	private Long hit;
	private String reg_date;
	private Long g_no;
	private Long o_no;
	private Long depth;
	private Long member_no;
	private String member_name;
	private Long counted;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public Long getG_no() {
		return g_no;
	}

	public void setG_no(Long g_no) {
		this.g_no = g_no;
	}

	public Long getO_no() {
		return o_no;
	}

	public void setO_no(Long o_no) {
		this.o_no = o_no;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Long getMember_no() {
		return member_no;
	}

	public void setMember_no(Long member_no) {
		this.member_no = member_no;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public Long getCounted() {
		return counted;
	}

	public void setCounted(Long counted) {
		this.counted = counted;
	}

}
