package edu.kh.jdbc.member.vo;

public class Member {

	private int memberNo; //회원번호
	private String memberId; // 회원 아이디
	private String memberPw; // 비밀번호
	private String memberName; //이름
	private String memberGender; //성별
	private String enrollDate; //가입일
	private String sescessionFlag; //탈퇴여부
	
	
	public Member () {
		
	}
	
	public Member(String memberId, String memberPw, String memberName, String memberGender) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberGender = memberGender;
		
	}

	// 매개변수 생성자
	public Member(int memberNo, String memberId, String memberName, String memberGender, String enrollDate) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberGender = memberGender;
		this.enrollDate = enrollDate;

	}

	// getter setter
	public int getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getMemberPw() {
		return memberPw;
	}


	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getMemberGender() {
		return memberGender;
	}


	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}


	public String getEnrollDate() {
		return enrollDate;
	}


	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}


	public String getSescessionFlag() {
		return sescessionFlag;
	}


	public void setSescessionFlag(String sescessionFlag) {
		this.sescessionFlag = sescessionFlag;
	}
	
	
}
