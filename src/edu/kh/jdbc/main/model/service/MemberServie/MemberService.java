package edu.kh.jdbc.main.model.service.MemberServie;

import edu.kh.jdbc.member.model.dao.MemberDAO.MemberDAO;
import edu.kh.jdbc.member.vo.Member;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;

public class MemberService {
	private MemberDAO dao = new MemberDAO();
	
	
	/** 내 정보 조회
	 * @param memberId
	 * @param memberPw1
	 * @param memberName
	 * @param memberGender
	 * @return myinfo
	 */
	public Member selectMyinfo(String memberId, String memberPw1, String memberName, String memberGender) throws Exception {
		
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		// 2. DAO 메서드 호출 후 결과 반환 받기 -- 갖고서 db에 접근
		 Member selectMyinfo = dao.selectMyinfo(conn, memberId, memberPw1, memberName, memberGender);
		
		//3, 커넥션 반환
		close(conn);
		
		
		return selectMyinfo;
	}
	
}
