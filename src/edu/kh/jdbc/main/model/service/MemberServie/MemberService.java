package edu.kh.jdbc.main.model.service.MemberServie;

import edu.kh.jdbc.member.model.dao.MemberDAO.MemberDAO;
import edu.kh.jdbc.member.vo.Member;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

public class MemberService {
	private MemberDAO dao = new MemberDAO();
	
	

	/**
	 * @return 
	 * @throws Exception
	 */
	public List<Member> selectAll() throws Exception{
		Connection conn = getConnection();
		
		List<Member> memberList = dao.selectAll(conn);
		
		close(conn);
		
		
		return memberList;
	}
	
	/** 정보 수정 서비스
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Member member) throws Exception {
		Connection conn = getConnection();
		
		
		int result = dao.updateMember(conn, member);
		
		//트랙젝션 필요
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 비밀번호 변경
	 * @param currentPw
	 * @param newPw1
	 * @param memberNo
	 * @return
	 */
	public int updatePw(String currentPw, String newPw1, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updatePw(conn, currentPw, newPw1, memberNo);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		return result;
	}
 
	/** 회원 탈퇴 서비스
	 * @param memberPw
	 * @param memberNo
	 * @return
	 */
	public int secession(String memberPw, int memberNo) throws Exception{
		Connection conn = getConnection();

		int result = dao.secession(conn, memberPw, memberNo);
		
		if(result > 0) commit(conn);
		else			rollback(conn);

		close(conn);
		return result;
	}

	
}
